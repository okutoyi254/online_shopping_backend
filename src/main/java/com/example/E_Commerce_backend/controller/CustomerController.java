package com.example.E_Commerce_backend.controller;

import com.example.E_Commerce_backend.dto.CreateAccountRequest;
import com.example.E_Commerce_backend.entity.Customer;
import com.example.E_Commerce_backend.repository.CustomerRepository;
import com.example.E_Commerce_backend.service.ServiceImplementation.CustomerServiceImplementation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerServiceImplementation customerService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerController(CustomerServiceImplementation customerService, PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create/account")
    public ResponseEntity<Map<String,Object>>createAccount(@Valid @RequestBody CreateAccountRequest createAccount){

        Customer customer=new Customer();
        customer.setEmail(createAccount.getEmail());
        customer.setFirstName(createAccount.getFirstName());
        customer.setPassword(passwordEncoder.encode(createAccount.getPassword()));
        customer.setPhoneNumber(createAccount.getPhoneNumber());
        customer.setLastName(createAccount.getLastName());
        customer.setHomeAddress(createAccount.getHomeAddress());

        customerService.createAccount(createAccount);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("status","SUCCESS","message","Account created successfully"));
    }




}
