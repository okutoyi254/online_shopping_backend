package com.example.E_Commerce_backend.repository;

import com.example.E_Commerce_backend.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products,Long> {
}
