package com.example.E_Commerce_backend.service.ServiceInterface;

import com.example.E_Commerce_backend.dto.AddToCart;
import com.example.E_Commerce_backend.dto.CreateAccountRequest;
import com.example.E_Commerce_backend.entity.CartItem;
import com.example.E_Commerce_backend.entity.Customer;
import com.example.E_Commerce_backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomerService {
    Customer createAccount(CreateAccountRequest createAccount);
    Page<Product> viewAvailableProducts(Pageable pageable, int categoryId);
    Page<Product> viewMyRecommendations(Long customerId, Pageable pageable);
    public CartItem addProductToCart(AddToCart addToCart);
    public void removeProductFromCart(Long productId);




}
