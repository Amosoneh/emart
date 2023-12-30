package com.emart.emart.services;

import com.emart.emart.datas.dtos.request.InitializePaymentRequest;

import com.emart.emart.datas.dtos.response.InitializePaymentResponse;
import com.emart.emart.datas.dtos.response.OrderResponse;
import com.emart.emart.datas.dtos.response.PaymentVerificationResponse;
import com.emart.emart.datas.dtos.response.VerifyPaymentResponse;
import com.emart.emart.datas.models.Order;
import com.emart.emart.exceptions.CartIsEmptyException;
import com.emart.emart.exceptions.UserNotFoundException;
import com.emart.emart.repositories.CartRepository;
import com.emart.emart.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service @RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerServiceImpl customerService;
    private final PaymentServiceImpl paymentService;

    private final CartRepository cartRepository;
    @Override
    public OrderResponse makeOrder(String customerId) throws UserNotFoundException {
        var customer = customerService.getCustomerById(customerId);
        var cart = customer.getCart();
        var ref = generateRef();
        if(cart.getItems().isEmpty()) throw new CartIsEmptyException("Cart is empty, add items to your cart first");
        Order order = Order.builder().orderedBy(customer.getName()).orderedAt(LocalDateTime.now())
                .cartId(cart.getId()).totalAmount(cart.getSubTotal())
                .referenceCode(ref).status("Pending").build();
        var savedOrder = orderRepository.save(order);

        InitializePaymentRequest paymentRequest = InitializePaymentRequest.builder().amount(savedOrder.getTotalAmount())
                .email(customer.getEmail()).currency("NGN").reference(ref).build();
        InitializePaymentResponse paymentResponse = paymentService.initializePayment(paymentRequest);
        return OrderResponse.builder().access_code(paymentResponse.getData().getAccess_code())
                .authorization_url(paymentResponse.getData().getAuthorization_url())
                .message(paymentResponse.getMessage())
                .reference(paymentResponse.getData().getReference())
                .status(paymentResponse.getData().getStatus())
                .orderId(savedOrder.getId())
                .build();
    }

    @Override
    public VerifyPaymentResponse verifyPayment(String reference) throws Exception {
        var order = orderRepository.findByReferenceCode(reference);
        var cart = cartRepository.findById(order.getCartId()).get();
        PaymentVerificationResponse response = paymentService.paymentVerification(reference);
        if(response.getStatus()){
            order.setStatus("Order Placed");
            order = orderRepository.save(order);
            cart.getItems().clear();
            cart.setSubTotal(BigDecimal.ZERO);
            cartRepository.save(cart);
            return VerifyPaymentResponse.builder().orderId(order.getId()).reference(response.getData().getReference())
                    .amount(response.getData().getAmount()).orderedAt(LocalDateTime.now()).message("Your order has been successfully placed").status(order.getStatus()).build();
        }
        return null;
    }

    public static String generateRef() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder referenceCode = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i <= 10; i++) {
            int index = random.nextInt(characters.length());
            referenceCode.append(characters.charAt(index));
        }

        return referenceCode.toString();
    }
}
