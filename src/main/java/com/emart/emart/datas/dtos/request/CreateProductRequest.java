package com.emart.emart.datas.dtos.request;

import lombok.*;

import java.math.BigDecimal;

@Setter @Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class CreateProductRequest {
    private Long userId;
    private String name;
    private String description;
    private Long price;
    private int quantities;
}
