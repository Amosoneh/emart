package com.emart.emart.controllers;

import com.emart.emart.datas.dtos.request.*;
import com.emart.emart.datas.dtos.response.OrderResponse;
import com.emart.emart.exceptions.EmailAlreadyExistException;
import com.emart.emart.exceptions.ProductNotFoundException;
import com.emart.emart.exceptions.UserNotFoundException;
import com.emart.emart.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor
@RequestMapping("/api")
public class AppController {
    private final CartServiceImpl cartService;
    private final CustomerServiceImpl customerService;
    private final ProductServiceImpl productService;
    private final OrderServiceImpl orderService;
    private final StoreServiceImp storeService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CustomerRegisterRequest request) throws EmailAlreadyExistException {
        return ResponseEntity.ok(customerService.register(request));
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(customerService.login(request.getEmail(), request.getPassword()));
    }

    @PostMapping("/store/create-store")
    public ResponseEntity<?> createStore(@RequestBody CreateStoreRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(storeService.createStore(request));
    }

    @PostMapping("/product/create-product")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping("/product/get-all-products")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/product/get-all-products/{productId}")
    public ResponseEntity<?> getAProduct(@PathVariable Long productId) throws ProductNotFoundException {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @PostMapping("/cart/add-to-cart")
    public ResponseEntity<?> addItemToCart(Long productId, Long userId) throws UserNotFoundException, ProductNotFoundException {
        return ResponseEntity.ok(cartService.addProductToCart(productId, userId));
    }

    @PostMapping("/order/make-order")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) throws UserNotFoundException {
        return ResponseEntity.ok(orderService.makeOrder(request));
    }

    @PostMapping("/order/payment/confirm/{reference}")
    public ResponseEntity<?> verifyOrder(@PathVariable String reference) throws Exception {
        return ResponseEntity.ok(orderService.verifyPayment(reference));
    }

    @GetMapping("/search")
    public ResponseEntity<?>searchProduct(@RequestParam String keyword){
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }
}


