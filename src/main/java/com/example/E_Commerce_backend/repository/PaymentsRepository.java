package com.example.E_Commerce_backend.repository;

import com.example.E_Commerce_backend.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payments,String> {
}
