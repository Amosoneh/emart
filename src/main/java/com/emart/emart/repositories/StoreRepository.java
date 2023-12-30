package com.emart.emart.repositories;

import com.emart.emart.datas.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, String> {
    Store findStoreByUserId(String userId);
}
