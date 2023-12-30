package com.emart.emart.services;

import com.emart.emart.datas.dtos.request.CustomerRegisterRequest;
import com.emart.emart.datas.models.Cart;
import com.emart.emart.datas.models.Customer;
import com.emart.emart.datas.models.Role;
import com.emart.emart.exceptions.EmailAlreadyExistException;
import com.emart.emart.exceptions.UserNotFoundException;
import com.emart.emart.repositories.CartRepository;
import com.emart.emart.repositories.CustomerRepository;
import com.emart.emart.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service @AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper mapper = new ModelMapper();

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CartRepository cartRepository;

    @Override
    public Customer register(CustomerRegisterRequest request) throws EmailAlreadyExistException {
        boolean anyMatch = customerRepository.findAll().stream().anyMatch(user -> user.getEmail().equals(request.getEmail()));
        if(anyMatch){
            throw new EmailAlreadyExistException("User already exists");
        }

        Customer customer = mapper.map(request, Customer.class);
        customer.setRole(Role.valueOf(request.getAccountType().toUpperCase()));
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        customer.setPassword(encodedPassword);
        customer.setCart(cartRepository.save(new Cart()));
        if(request.getAccountType() != null && request.getAccountType().equalsIgnoreCase(Role.SELLER.name())){
            customer.setRole(Role.SELLER);
        }else customer.setRole(Role.BUYER);
        customer = customerRepository.save(customer);
        return customer;
    }

    @Override
    public Map<String, String> login(String email, String password) throws UserNotFoundException {
        var user = customerRepository.findCustomerByEmail(email);
        String token = "";
        if (user == null ) throw new UserNotFoundException("User not found");
        else {
            if (passwordEncoder.matches(password, user.getPassword())) {
                    token = jwtService.generateToken(user);
                } else {
                    return Map.of("error", "Bad login credentials");
                }
                return Map.of("token", token);
        }
    }

    public Customer getCustomerById(String id) throws UserNotFoundException {
        var customer =  customerRepository.findCustomerById(id);
        if(customer == null){
            throw new UserNotFoundException("User not found");
        }
        return customer;
    }
}
