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
    @Column(name = "transaction_code")
    private String transactionCode;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "payment_amount")
    private double payedAmount;

    @OneToOne
    @JoinColumn(name = "order_id",nullable = false,unique = true)
    private Order order;


}
