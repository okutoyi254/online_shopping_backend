package com.example.E_Commerce_backend.exceptions;


import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

public class GlobalExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<Map<String,String>>handleNotFound(ItemNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",ex.getMessage()));
    }

    @ExceptionHandler(PhoneNumberAlreadyRegisteredException.class)
    public ResponseEntity<Map<String,String>>handlePhoneNoRegistered(PhoneNumberAlreadyRegisteredException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", exception.getMessage()));
    }
}
