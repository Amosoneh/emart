package com.emart.emart.datas.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder @NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentVerificationResponse {

    private Boolean status;
    private String message;
    private Data data;

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        private String status;
        private String reference;
        private BigDecimal amount;
        private String gateway_response;
        private String paid_at;
        private String created_at;
        private String channel;
        private String currency;
        private String ip_address;
        private String plan;
        private String message;
        private String authorization_code;
        private Customer customer;

        @lombok.Data
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Customer{
            private String email;
        }

    }
}
