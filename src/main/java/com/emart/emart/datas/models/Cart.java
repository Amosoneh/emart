package com.emart.emart.datas.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.List;

@Setter @Entity @Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Cart {
    @Id
    @UuidGenerator
    private String id;
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderItem> items;
    @Builder.Default
    private BigDecimal subTotal = BigDecimal.ZERO;
}
