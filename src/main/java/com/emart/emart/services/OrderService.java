package com.emart.emart.services;

import com.emart.emart.datas.dtos.request.OrderRequest;
import com.emart.emart.datas.dtos.response.OrderResponse;
import com.emart.emart.datas.dtos.response.VerifyPaymentResponse;
import com.emart.emart.exceptions.UserNotFoundException;

public interface OrderService {
    OrderResponse makeOrder(OrderRequest request) throws UserNotFoundException;
    VerifyPaymentResponse verifyPayment(String reference) throws Exception;
}
