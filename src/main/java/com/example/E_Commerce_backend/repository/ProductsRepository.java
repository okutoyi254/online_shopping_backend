package com.example.E_Commerce_backend.repository;

import com.example.E_Commerce_backend.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products,Long> {

    @Query(value = "SELECT * FROM products WHERE stock_quantity >0")
    Page<Products> getAvailableProducts(Pageable pageable, int categoryId);
    boolean existsByProductId(Long productId);
    Optional<Products>findByProductId(Long productId);
}
