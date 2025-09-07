package com.example.E_Commerce_backend.service.ServiceInterface;

import com.example.E_Commerce_backend.dto.CreateAccountRequest;
import com.example.E_Commerce_backend.entity.Products;
import org.hibernate.query.Page;

import java.util.List;

public interface BusinessServices {

    void calculateDiscount(Long productId,int quantity);
    List<Products>productsWithHighestSales();
    List<Products>productsWithLowestSales();
    Double calculateTotalAmount();




}
