package com.example.E_Commerce_backend.repository;

import com.example.E_Commerce_backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    public Cart findByCustomer_customerId(Long customerId);
}
