package com.example.E_Commerce_backend.repository;

import com.example.E_Commerce_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Product,Long> {

    boolean existsByProductId(Long productId);
    Optional<Product>findByProductId(Long productId);
}
