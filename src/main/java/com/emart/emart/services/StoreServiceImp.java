package com.emart.emart.services;

import com.emart.emart.datas.dtos.request.CreateStoreRequest;
import com.emart.emart.datas.models.Store;
import com.emart.emart.exceptions.UserNotFoundException;
import com.emart.emart.repositories.CustomerRepository;
import com.emart.emart.repositories.StoreRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class StoreServiceImp implements StoreService {
    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final CustomerRepository customerRepository;
    @Override
    public String createStore(CreateStoreRequest request) throws UserNotFoundException {
        var customer = customerRepository.findCustomerById(request.getUserId());
        if(customer == null){
            throw new UserNotFoundException("User not found");
        }
        var store = modelMapper.map(request, Store.class);
        store.setUserId(customer.getId());
        storeRepository.save(store);
        return "Store created successfully";
    }
}
