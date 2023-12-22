package com.emart.emart.datas.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter @Getter @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;
    private int quantities;

}
