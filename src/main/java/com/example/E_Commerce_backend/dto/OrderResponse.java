package com.example.E_Commerce_backend.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class OrderResponse {

    @Id
    private Long orderId;
    private int totalItems;
    private double totalCost;
    private String customerAddress;
}
