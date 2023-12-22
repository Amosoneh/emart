package com.emart.emart.services;

import com.emart.emart.datas.dtos.request.InitializePaymentRequest;
import com.emart.emart.datas.dtos.response.InitializePaymentResponse;
import com.emart.emart.datas.dtos.response.PaymentVerificationResponse;
import com.emart.emart.datas.models.Payment;
import com.emart.emart.repositories.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.emart.emart.constants.APIConstant.*;


@Service @RequiredArgsConstructor
@Transactional @Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Value("${app.SecretKey}")
    private String paystackSecretKey;
    private final PaymentRepository paymentRepository;


    @Override
    public InitializePaymentResponse initializePayment(InitializePaymentRequest initializePayment) {
        InitializePaymentResponse initializePaymentResponse = null;

        try {
            log.info("Initialize request: {}", initializePayment);
            Gson gson = new Gson();
            StringEntity postingString = new StringEntity(gson.toJson(initializePayment));
            HttpClient client = HttpClientBuilder.create().build();
            log.info("Client: {}", client.toString());
            HttpPost post = new HttpPost(PAYSTACK_INITIALIZE_PAY);
            log.info("Post: {}",post);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);
            log.info("response: {}", response);
            log.info("Paystack Initialization Request: {}", gson.toJson(initializePayment));
            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                log.info("Initialize reader: {}",rd);
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);

                    log.info("Paystack Initialization Response: {}", result);
                }
            } else {
                log.error("Paystack initialization failed with status code: {}", response.getStatusLine().getStatusCode());
                log.error("Paystack initialization response: {}", result);
                throw new Exception("Paystack is unable to initialize payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            initializePaymentResponse = mapper.readValue(result.toString(), InitializePaymentResponse.class);
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
        return initializePaymentResponse;
    }


    @Override
    public PaymentVerificationResponse paymentVerification(String reference) throws Exception {
        PaymentVerificationResponse paymentVerificationResponse;
        Payment payment = null;


        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PAYSTACK_VERIFY + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to verify payment at the moment");
            }
            log.info("Result from paystack: {}", result);

            ObjectMapper mapper = new ObjectMapper();
            paymentVerificationResponse = mapper.readValue(result.toString(), PaymentVerificationResponse.class);

            if (paymentVerificationResponse == null || paymentVerificationResponse.getStatus().equals(false)) {
                throw new Exception("An error");
            } else if (paymentVerificationResponse.getData().getStatus().equalsIgnoreCase("success")) {

                payment = Payment.builder()
                        .reference(paymentVerificationResponse.getData().getReference())
                        .totalAmount(paymentVerificationResponse.getData().getAmount())
                        .paidAt(paymentVerificationResponse.getData().getPaid_at())
                        .createdAt(paymentVerificationResponse.getData().getCreated_at())
                        .currency(paymentVerificationResponse.getData().getCurrency())
                        .transactionDate(paymentVerificationResponse.getData().getPaid_at())
                        .paymentStatus(paymentVerificationResponse.getData().getStatus())
                        .build();
            }
        } catch (Exception ex) {
            log.info("Exception: {}",ex.getMessage());
            throw new Exception(ex.getMessage());
        }
        if(payment != null) {
            paymentRepository.save(payment);

        }
        return paymentVerificationResponse;
    }

}
