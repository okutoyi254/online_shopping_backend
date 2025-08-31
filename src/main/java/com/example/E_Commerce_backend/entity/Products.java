package com.example.E_Commerce_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Products {

    @Id
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int initialQuantity;

    private int stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductsCategory category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem>orderItems=new ArrayList<>();
}
