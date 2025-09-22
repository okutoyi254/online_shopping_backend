package com.example.E_Commerce_backend.exceptions;

public class InsufficientAccountBalanceException extends RuntimeException {
    public InsufficientAccountBalanceException(String message) {
        super(message);
    }
}
