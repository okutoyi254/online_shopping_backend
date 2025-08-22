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

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long orderId;

    private double totalAmount;

    private LocalDateTime paymentDate;

    private double discount;

    private double payedAmount;



    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
