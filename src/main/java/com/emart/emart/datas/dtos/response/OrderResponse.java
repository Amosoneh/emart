package com.emart.emart.datas.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder @AllArgsConstructor  @NoArgsConstructor
public class OrderResponse {
    private String orderId;
    private String message;
    private String authorization_url;
    private String access_code;
    private String status;
    private String reference;
}
