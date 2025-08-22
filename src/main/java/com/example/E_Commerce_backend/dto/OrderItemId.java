package com.example.E_Commerce_backend.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class OrderItemId implements Serializable {

    private Long orderId;
    private Long productId;
}

