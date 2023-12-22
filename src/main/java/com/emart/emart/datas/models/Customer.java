package com.emart.emart.datas.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Setter @Getter
@Entity @AllArgsConstructor
@NoArgsConstructor @ToString
public class Customer extends AppUser {
    @OneToOne
    @Cascade(CascadeType.REMOVE)
    private Cart cart = new Cart();
}
