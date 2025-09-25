package com.example.E_Commerce_backend.service.ServiceInterface;

import com.example.E_Commerce_backend.entity.Product;

import java.util.List;

public interface BusinessServices {

    void calculateDiscount(Long productId,int quantity);
    List<Product>productsWithHighestSales();
    List<Product>productsWithLowestSales();
    Double calculateTotalAmount();
    void  placeOrder(Long customerId, double paymentAmount);
}
