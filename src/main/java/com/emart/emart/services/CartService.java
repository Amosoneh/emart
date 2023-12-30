package com.emart.emart.services;

import com.emart.emart.exceptions.ProductNotFoundException;
import com.emart.emart.exceptions.UserNotFoundException;

public interface CartService {
    String addProductToCart(String productId, String userId) throws UserNotFoundException, ProductNotFoundException;
}
