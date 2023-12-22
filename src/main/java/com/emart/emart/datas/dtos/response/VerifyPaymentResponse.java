package com.emart.emart.datas.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class VerifyPaymentResponse {
    private String orderId;
    private String status;
    private String reference;
    private BigDecimal amount;
    private String message;
    private LocalDateTime orderedAt;
}
