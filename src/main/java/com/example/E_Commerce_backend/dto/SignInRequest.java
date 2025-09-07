package com.example.E_Commerce_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignInRequest {

    @NotBlank(message = "this field is required")
    private String phoneNo;

    @NotBlank(message = "please enter your password ")
    private String password;
}
