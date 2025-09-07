package com.example.E_Commerce_backend.repository;

import com.example.E_Commerce_backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
