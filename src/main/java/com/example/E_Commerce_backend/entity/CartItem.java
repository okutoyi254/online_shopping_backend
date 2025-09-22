package com.example.E_Commerce_backend.entity;

import com.example.E_Commerce_backend.dto.CartItemId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cart_item")
public class CartItem {

    @EmbeddedId
    private CartItemId cartItemId=new CartItemId();


    private int quantity;
    private Double unitPrice;
    private double discount;
    private Double totalPrice;

   @ManyToOne
   @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    private Cart cart;

   @ManyToOne
   @MapsId("productId")
   @JoinColumn(name = "product_id")
    private Products products;
}
