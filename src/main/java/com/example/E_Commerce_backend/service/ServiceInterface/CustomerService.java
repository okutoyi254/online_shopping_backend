package com.example.E_Commerce_backend.service.ServiceInterface;

import com.example.E_Commerce_backend.dto.CreateAccountRequest;
import com.example.E_Commerce_backend.dto.OrderRequest;

import java.awt.print.Pageable;

public interface CustomerService {
    void createAccount(CreateAccountRequest createAccount);
    Pageable viewAvailableProducts();
    void placeOrder(OrderRequest orderRequest);
    Pageable viewMyRecommendations(Long customerId);



}
