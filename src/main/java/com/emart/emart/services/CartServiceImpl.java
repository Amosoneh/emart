package com.emart.emart.services;

import com.emart.emart.datas.models.Cart;
import com.emart.emart.datas.models.OrderItem;
import com.emart.emart.exceptions.ProductNotFoundException;
import com.emart.emart.exceptions.UserNotFoundException;
import com.emart.emart.repositories.CartRepository;
import com.emart.emart.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor @Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CustomerServiceImpl customerService;
    private final ProductService productService;

    private final CustomerRepository customerRepository;
    @Override
    public String addProductToCart(Long productId, Long userId) throws UserNotFoundException, ProductNotFoundException {
        var customer = customerRepository.findCustomerById(userId);
        if(customer == null) throw new UserNotFoundException("User not found");
        log.info("Customer: {}", customer.toString());
        var product = productService.getProduct(productId);
        var cart = customer.getCart();
        OrderItem item = OrderItem.builder().product(product).quantity(1).build();
        cart.getItems().add(item);
        cartRepository.save(updateCartSubTotal(cart));
        return "Product added to cart successfully";
    }

    private Cart updateCartSubTotal(Cart cart) {
        cart.getItems().forEach(item -> sumCartItemPrice(cart, item));
        return cart;
    }

    private static void sumCartItemPrice(Cart cart, OrderItem item) {
        var itemPrice = item.getProduct().getPrice();
        cart.setSubTotal(cart.getSubTotal().add(itemPrice));
    }
}
