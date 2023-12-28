package com.emart.emart.services;

import com.emart.emart.datas.dtos.request.CustomerRegisterRequest;
import com.emart.emart.datas.models.Customer;
import com.emart.emart.exceptions.EmailAlreadyExistException;
import com.emart.emart.exceptions.UserNotFoundException;

import java.util.Map;

public interface CustomerService {
    Customer register(CustomerRegisterRequest request) throws EmailAlreadyExistException;
    Map<String, String> login(String email, String password) throws UserNotFoundException;
}
