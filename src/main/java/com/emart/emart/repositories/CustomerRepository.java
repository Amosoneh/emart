package com.emart.emart.repositories;

import com.emart.emart.datas.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByEmail(String email);

    Customer findCustomerById(Long userId);
}

