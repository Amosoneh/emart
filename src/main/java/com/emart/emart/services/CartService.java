package com.emart.emart.services;

import com.emart.emart.exceptions.ProductNotFoundException;
import com.emart.emart.exceptions.UserNotFoundException;

public interface CartService {
    String addProductToCart(Long productId, Long userId) throws UserNotFoundException, ProductNotFoundException;
}
