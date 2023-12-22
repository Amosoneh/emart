package com.emart.emart.datas.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class OrderRequest {
    private Long cartId;
    private BigDecimal totalAmount;
    private Long customerId;
}
