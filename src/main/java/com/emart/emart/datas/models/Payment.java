package com.emart.emart.datas.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;

@Setter @Getter @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Payment {
    @Id
    @UuidGenerator
    private String id;
    private String customerId;
    private String transactionDate;
    private BigDecimal totalAmount;
    private String paymentStatus;

    private String currency;
    private String reference;
    private String createdAt;
    private String paidAt;
}
