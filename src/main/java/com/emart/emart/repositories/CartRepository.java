package com.emart.emart.repositories;

import com.emart.emart.datas.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
