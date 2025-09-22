package com.example.E_Commerce_backend.service.ServiceInterface;

import com.example.E_Commerce_backend.dto.AddToCart;
import com.example.E_Commerce_backend.dto.CreateAccountRequest;
import com.example.E_Commerce_backend.entity.Cart;
import com.example.E_Commerce_backend.entity.Products;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface CustomerService {
    void createAccount(CreateAccountRequest createAccount);
    Page<Products> viewAvailableProducts(Pageable pageable,int categoryId);
    Page<Products> viewMyRecommendations(Long customerId, Pageable pageable);
    public void addProductToCart(AddToCart addToCart);
    public void removeProductFromCart(Long productId);




}
