package com.example.E_Commerce_backend.repository;

import com.example.E_Commerce_backend.dto.CartItemId;
import com.example.E_Commerce_backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    CartItem findByCartId(Long cartId);

    CartItem findByProductId(Long productId);
}
