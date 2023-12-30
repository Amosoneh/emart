package com.emart.emart.datas.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequest {
    private String productId;
    private String userId;
}
