package com.emart.emart.datas.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@Entity @Builder
@Document(indexName = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Keyword)
    private String description;


    @Builder.Default
    @Field(type = FieldType.Float)
    private BigDecimal price = BigDecimal.ZERO;

    @Field(type = FieldType.Integer)
    private int quantities;

}
