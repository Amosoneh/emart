package com.emart.emart.datas.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Setter @Getter @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity
public class OrderItem {
    @Id
    @UuidGenerator
    private String id;
    @OneToOne
    private Product product;
    private int quantity;
}
