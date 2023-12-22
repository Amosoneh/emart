package com.emart.emart.datas.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor
@NoArgsConstructor
public class CreateStoreRequest {
    private Long id;
    private Long userId;
    private String name;
    private String description;
}
