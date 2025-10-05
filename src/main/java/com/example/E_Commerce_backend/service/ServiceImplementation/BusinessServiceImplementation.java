package com.example.E_Commerce_backend.service.ServiceImplementation;

import com.example.E_Commerce_backend.entity.*;
import com.example.E_Commerce_backend.exceptions.InsufficientAccountBalanceException;
import com.example.E_Commerce_backend.repository.*;
import com.example.E_Commerce_backend.service.ServiceInterface.BusinessServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BusinessServiceImplementation implements BusinessServices {

    private final ProductsRepository productsRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final PaymentsRepository paymentsRepository;
    private final OrderItemsRepository orderItemsRepository;

    public BusinessServiceImplementation(ProductsRepository productsRepository, CartItemRepository cartItemRepository,
                                         CustomerRepository customerRepository, CartRepository cartRepository,
                                         OrderRepository orderRepository,
                                         PaymentsRepository paymentsRepository, OrderItemsRepository orderItemsRepository) {
        this.productsRepository = productsRepository;
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.paymentsRepository = paymentsRepository;
        this.orderItemsRepository = orderItemsRepository;
    }

    @Override
    public double calculateDiscount(Long productId, int quantity) {
        Product product = productsRepository.findByProductId(productId).orElseThrow();
        double unitPrice = product.getPrice();
        double totalPrice = unitPrice * quantity;

        return 0.0;

    }

    @Override
    public List<Product> productsWithHighestSales() {
        return List.of();
    }

    @Override
    public List<Product> productsWithLowestSales() {
        return List.of();
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Override
    public Order placeOrder(Long customerId, double paymentAmount) {

        double totalPrice = calculateTotalPrice(customerId);

        if (Math.abs(paymentAmount - totalPrice) > 0.01) {
            throw new InsufficientAccountBalanceException("You have insufficient balance to complete the transaction");
        }

        Cart cart = cartRepository.findByCustomer_customerId(customerId);
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("No items available in the cart to place an order");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer id not found"));

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setCustomer(customer);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        persistPaymentInformation(order, paymentAmount, customerId);
        persistToOrderItems(customerId, order);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return order;
    }



    //Persist to the payment database
    public void persistPaymentInformation(Order order, double paymentAmount, Long customerId) {
        double totalPrice = calculateTotalPrice(customerId);
        Payments payments = new Payments();
        payments.setOrder(order);
        payments.setPaymentDate(LocalDateTime.now());
        payments.setPayedAmount(paymentAmount);
        payments.setTotalAmount(totalPrice);
        payments.setTransactionCode(UUID.randomUUID().toString());
        paymentsRepository.save(payments);
    }



    // persist to the orderItem database
    public void persistToOrderItems(Long customerId, Order order) {
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

    public double calculateTotalPrice(long customerId) {
        Cart cart = cartRepository.findByCustomer_customerId(customerId);
        System.out.println(customerId);
        System.out.println(cart.getCartItems().get(0).getProduct().getProductId());
        if (cart.getCartItems() == null || cart.getCartItems().isEmpty())
            throw new RuntimeException("No items available in the cart to place an order");

        double totalCost = 0;
        for (CartItem cartItem : cart.getCartItems()) {
            totalCost += cartItem.getTotalPrice();
        }
        return totalCost;


    }
}