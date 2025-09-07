package com.example.E_Commerce_backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderRequest {

   @Min(value = 1,message = "please provide a valid product id")
    private Long productId;

    @Min(value = 1,message = "the minimum number of items to order is 1")
    private int quantity;

}
