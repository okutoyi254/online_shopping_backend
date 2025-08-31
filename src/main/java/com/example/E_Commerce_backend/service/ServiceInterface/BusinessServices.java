package com.example.E_Commerce_backend.service.ServiceInterface;

import com.example.E_Commerce_backend.dto.CreateAccountRequest;

public interface BusinessServices {

    void createAccount(CreateAccountRequest accountRequest);
    void deleteAccount(Long id);
    void updateAccountDetails(Long id,CreateAccountRequest accountRequest);
    void placeOrder();
}
