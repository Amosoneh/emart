package com.emart.emart.datas.dtos.request;

import lombok.*;

@Setter @Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
