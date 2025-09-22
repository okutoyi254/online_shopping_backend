package com.example.E_Commerce_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;


    @OneToOne
    @JoinColumn(name = "customer_id",nullable = false,unique = true)
    private Customer customer;

    @OneToMany(mappedBy = "cart",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
}
