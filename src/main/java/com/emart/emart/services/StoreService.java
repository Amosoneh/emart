package com.emart.emart.services;

import com.emart.emart.datas.dtos.request.CreateStoreRequest;
import com.emart.emart.datas.models.Store;
import com.emart.emart.exceptions.UserNotFoundException;

public interface StoreService {
    Store createStore(CreateStoreRequest request) throws UserNotFoundException;
}
