package com.emart.emart.datas.models;

import jakarta.persistence.*;
import lombok.*;

@Setter @Getter @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Product product;
    private int quantity;
}
