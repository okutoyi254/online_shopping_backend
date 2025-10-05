package com.example.E_Commerce_backend.service.ServiceInterface;

import com.example.E_Commerce_backend.entity.Order;
import com.example.E_Commerce_backend.entity.Product;

import java.util.List;

public interface BusinessServices {

    double calculateDiscount(Long productId, int quantity);
    List<Product>productsWithHighestSales();
    List<Product>productsWithLowestSales();
    Order placeOrder(Long customerId, double paymentAmount);
}
