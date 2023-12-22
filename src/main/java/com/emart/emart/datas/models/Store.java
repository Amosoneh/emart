package com.emart.emart.datas.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.Set;

@Setter @Getter @Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String name;
    private String description;
    @OneToMany(fetch =FetchType.EAGER ,cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Product> products = new HashSet<>();
}
