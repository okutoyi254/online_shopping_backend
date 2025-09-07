package com.example.E_Commerce_backend.exceptions;

public class PhoneNumberAlreadyRegisteredException extends RuntimeException{
    public PhoneNumberAlreadyRegisteredException(String message) {
        super(message);
    }
}
