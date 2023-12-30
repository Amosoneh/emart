package com.emart.emart.datas.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter @Getter @Builder @AllArgsConstructor
@NoArgsConstructor @Entity
@Table(name = "orders")
public class Order {
    @Id
    @UuidGenerator
    private String id;
    private String orderedBy;
    private BigDecimal totalAmount;
    private String cartId;
    private LocalDateTime orderedAt;
    private String status;
    private String referenceCode;
}
