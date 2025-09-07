package com.example.E_Commerce_backend.service.ServiceImplementation;

import com.example.E_Commerce_backend.dto.CreateAccountRequest;
import com.example.E_Commerce_backend.dto.OrderRequest;
import com.example.E_Commerce_backend.entity.Customer;
import com.example.E_Commerce_backend.exceptions.PhoneNumberAlreadyRegisteredException;
import com.example.E_Commerce_backend.repository.CustomerRepository;
import com.example.E_Commerce_backend.service.ServiceInterface.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.print.Pageable;

@Service
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public void createAccount(@Valid @RequestBody CreateAccountRequest createAccount){
        if(customerRepository.existsByEmail(createAccount.getEmail())||
                customerRepository.existsByPhoneNo(createAccount.getPhoneNumber())){
            throw new PhoneNumberAlreadyRegisteredException("Phone number already exists!");
        }
        Customer customer=new Customer();
        customer.setEmail(createAccount.getEmail());
        customer.setPassword(createAccount.getPassword());
        customer.setFirstName(createAccount.getFirstName());
        customer.setLastName(createAccount.getLastName());
        customer.setPhoneNumber(createAccount.getPhoneNumber());
        customer.setHomeAddress(createAccount.getHomeAddress());
        customerRepository.save(customer);
    }

    @Override
    public Pageable viewAvailableProducts() {
        return null;
    }

    @Override
    public void placeOrder(OrderRequest orderRequest) {

    }

    @Override
    public Pageable viewMyRecommendations(Long customerId) {
        return null;
    }


}
