package com.example.E_Commerce_backend.service.ServiceInterface;

import com.example.E_Commerce_backend.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    public CartItem addItemToCart(CartItem cartItem);
    public  void deleteFromCart(Long productId);
    public Page<CartItem>viewCartItems(Pageable pageable);
    public Double calculateTotalCostPerItem();
    public Double calculateTotalCostOfItems();

}
