package com.example.E_Commerce_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_Id;


    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    private Payments payments;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem>orderItems=new ArrayList<>();
}
