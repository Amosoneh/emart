package com.emart.emart.services;

import com.emart.emart.datas.dtos.request.CreateProductRequest;
import com.emart.emart.datas.models.Product;
import com.emart.emart.exceptions.ProductNotFoundException;
import com.emart.emart.exceptions.UserNotFoundException;

import java.util.List;

public interface ProductService {
    String createProduct(CreateProductRequest request) throws UserNotFoundException;
    void deleteProduct(Long productId) throws ProductNotFoundException;
    Product getProduct(Long productId) throws ProductNotFoundException;
    List<Product> getAllProducts();
}
