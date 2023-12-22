package com.emart.emart.services;

import com.emart.emart.datas.dtos.request.InitializePaymentRequest;
import com.emart.emart.datas.dtos.response.InitializePaymentResponse;
import com.emart.emart.datas.dtos.response.PaymentVerificationResponse;

public interface PaymentService {
    InitializePaymentResponse initializePayment(InitializePaymentRequest initializePayment);


    PaymentVerificationResponse paymentVerification(String reference) throws Exception;
}
