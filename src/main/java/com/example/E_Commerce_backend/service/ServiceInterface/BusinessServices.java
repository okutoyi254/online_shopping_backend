package com.example.E_Commerce_backend.service.ServiceInterface;

import com.example.E_Commerce_backend.entity.Products;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BusinessServices {

    void calculateDiscount(Long productId,int quantity);
    List<Products>productsWithHighestSales();
    List<Products>productsWithLowestSales();
    Double calculateTotalAmount();
    void  placeOrder(Long customerId, double paymentAmount);
}
