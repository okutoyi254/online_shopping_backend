package com.example.E_Commerce_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAccountRequest {

    private  Long id;

    @NotEmpty(message ="Id number is required")
    private String firstName;
    @NotBlank(message = "this field is required")
    private String lastName;
    @NotBlank(message = "this field is required")
    private String homeAddress;
    @NotBlank(message = "this field is required")
    private String phoneNumber;
    @Email(message = "please provide a valid email")
    private String email;
    @NotBlank(message = "this field is required")
    private String password;
}
