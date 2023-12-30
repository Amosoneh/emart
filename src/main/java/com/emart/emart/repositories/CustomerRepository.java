package com.emart.emart.repositories;

import com.emart.emart.datas.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findCustomerByEmail(String email);

    Customer findCustomerById(String userId);
}

