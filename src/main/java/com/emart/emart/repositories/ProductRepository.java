package com.emart.emart.repositories;

import com.emart.emart.datas.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long productId);

    Product findProductByName(String productName);
}
