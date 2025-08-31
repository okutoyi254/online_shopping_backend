package com.example.E_Commerce_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payment_records")
public class Payments {

    @Id
    private String transactionCode;

    private double totalAmount;

    private LocalDateTime paymentDate;

    private double payedAmount;

    @OneToOne
    @JoinColumn(name = "order_id",nullable = false,unique = true)
    private Order order;


}
