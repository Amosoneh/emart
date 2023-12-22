package com.emart.emart.services;

import com.emart.emart.datas.dtos.request.CreateStoreRequest;
import com.emart.emart.exceptions.UserNotFoundException;

public interface StoreService {
    String createStore(CreateStoreRequest request) throws UserNotFoundException;
}
