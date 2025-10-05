package com.example.E_Commerce_backend.entity;

import com.example.E_Commerce_backend.dto.CartItemId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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
    private Product product;

   @Override
    public boolean equals(Object o){
       if(this==o) return true;

       if(!(o instanceof CartItem that)) return false;
       return Objects.equals(cartItemId,that.cartItemId);
   }

   @Override
    public int hashCode(){
       return Objects.hash(cartItemId);
   }
}
