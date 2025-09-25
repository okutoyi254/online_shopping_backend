package com.example.E_Commerce_backend.service.ServiceImplementation;

import com.example.E_Commerce_backend.entity.*;
import com.example.E_Commerce_backend.exceptions.InsufficientAccountBalanceException;
import com.example.E_Commerce_backend.repository.*;
import com.example.E_Commerce_backend.service.ServiceInterface.BusinessServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BusinessServiceImplementation implements BusinessServices {

     private ProductsRepository productsRepository;
     private CartItemRepository cartItemRepository;
     private CustomerRepository customerRepository;
     private CartRepository cartRepository;
     @Autowired private OrderRepository orderRepository;
     @Autowired private PaymentsRepository paymentsRepository;
     @Autowired private OrderItemsRepository orderItemsRepository;

    public BusinessServiceImplementation(ProductsRepository productsRepository, CartRepository cartRepository) {
        this.productsRepository = productsRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public void calculateDiscount(Long productId, int quantity) {

    }

    @Override
    public List<Product> productsWithHighestSales() {
        return List.of();
    }

    @Override
    public List<Product> productsWithLowestSales() {
        return List.of();
    }

    @Override
    public Double calculateTotalAmount() {
        return 0.0;
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Override
    public void  placeOrder(Long customerId, double paymentAmount) {

        //Confirm if the paid amount is the total cost of the items purchased
        if(paymentAmount !=calculateTotalAmount()){
            throw new InsufficientAccountBalanceException("You have insufficient balance to complete the transaction");
        }

        Cart cart = cartRepository.findByCustomer_customerId(customerId);
        if (cart == null || cart.getCartItems() == null) {
            throw new RuntimeException("No items available in the cart to place an order");
        }
        //Persist to the order database
        Customer customer=customerRepository.findById(1L).orElseThrow(()->new RuntimeException("Customer id not found"));
            Order order=new Order();
            order.setOrderDate(LocalDateTime.now());
            order.setCustomer(customer);
            order.setTotalPrice(calculateTotalAmount());
            orderRepository.save(order);


            persistPaymentInformation(order,paymentAmount);
            persistToOrderItems(customerId,order);


           cart.getCartItems().clear();
           cartRepository.save(cart);

    }


    //Persist to the payment database
    public void persistPaymentInformation(Order order,double paymentAmount){
        Payments payments=new Payments();
        payments.setOrder(order);
        payments.setPaymentDate(LocalDateTime.now());
        payments.setPayedAmount(paymentAmount);
        payments.setTotalAmount(calculateTotalAmount());
        payments.setTransactionCode("GFHDJ");
        paymentsRepository.save(payments);
    }


    // persist to the orderItem database
    public void persistToOrderItems(Long customerId,Order order) {
        Cart cart = cartRepository.findByCustomer_customerId(customerId);
        if (cart == null || cart.getCartItems() == null) {
            throw new RuntimeException("No items available in the cart to place an order");
        }
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setTotalCost((cartItem.getQuantity() * cartItem.getUnitPrice()) - cartItem.getDiscount());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItemsRepository.save(orderItem);

        }
    }


}
