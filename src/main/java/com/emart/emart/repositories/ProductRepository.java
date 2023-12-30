package com.emart.emart.repositories;

import com.emart.emart.datas.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findProductById(String productId);

    Product findProductByName(String productName);
}
