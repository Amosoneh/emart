package com.emart.emart.repositories;

import com.emart.emart.datas.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
    Order findByReferenceCode(String referenceCode);
}
