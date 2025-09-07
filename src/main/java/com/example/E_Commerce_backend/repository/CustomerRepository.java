package com.example.E_Commerce_backend.repository;

import com.example.E_Commerce_backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByPhoneNo(String phoneNo);
    boolean existsByEmail(String email);
}
