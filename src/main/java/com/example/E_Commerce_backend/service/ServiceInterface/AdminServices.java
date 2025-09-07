package com.example.E_Commerce_backend.service.ServiceInterface;

import org.hibernate.query.Page;

public interface AdminServices {
    Page viewAllProducts();
    Page viewAllCustomers();
    Page viewAllPendingOrders();
}
