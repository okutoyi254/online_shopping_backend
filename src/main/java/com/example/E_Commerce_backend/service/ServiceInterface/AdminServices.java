package com.example.E_Commerce_backend.service.ServiceInterface;

import com.example.E_Commerce_backend.entity.Customer;
import com.example.E_Commerce_backend.entity.Order;
import com.example.E_Commerce_backend.entity.Products;
import org.springframework.data.domain.Page;


import java.awt.print.Pageable;

public interface AdminServices {
    Page<Products> viewAllProducts(Pageable pageable,int categoryID);
    Page<Customer>viewAllCustomers();
    Page<Order>viewAllPendingOrders();
}
