package com.example.E_Commerce_backend.repository;

import com.example.E_Commerce_backend.dto.OrderItemId;
import com.example.E_Commerce_backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem, OrderItemId> {
}
