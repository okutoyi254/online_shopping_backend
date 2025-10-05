package com.example.E_Commerce_backend;

import com.example.E_Commerce_backend.dto.OrderItemId;
import com.example.E_Commerce_backend.entity.*;
import com.example.E_Commerce_backend.repository.*;
import com.example.E_Commerce_backend.service.ServiceImplementation.BusinessServiceImplementation;
import com.example.E_Commerce_backend.service.ServiceImplementation.CustomerServiceImplementation;
import com.example.E_Commerce_backend.service.ServiceInterface.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BusinessServiceImplementationTest {

    @Mock
    private CartRepository cartRepository;

    @Mock private ProductsRepository productsRepository;

    @Mock private CartItemRepository cartItemRepository;

    @Mock private PaymentsRepository paymentsRepository;

    @Mock private OrderRepository orderRepository;

    @Mock private CustomerRepository customerRepository;

    @Mock private OrderItemsRepository orderItemsRepository;

    @InjectMocks
    private BusinessServiceImplementation businessService;

    @InjectMocks
    private CustomerServiceImplementation customerService;


    private Product product;
    private Cart cart;
    private CartItem cartItem;
    private Customer customer;
    private OrderItem orderItem;
    private  Order order;

    @BeforeEach
    void setup(){
      product=new Product();
      product.setProductId(1L);
      product.setStockQuantity(100);
      product.setPrice(100);

      cart=new Cart();
      cart.setCartId(2L);
      cart.setCartItems(new ArrayList<>());

      cartItem=new CartItem();
      cartItem.setProduct(product);
      cartItem.setQuantity(10);
      cartItem.setDiscount(100);
      cartItem.setUnitPrice(100D);
      cartItem.setTotalPrice(product.getPrice()*cartItem.getQuantity()- cartItem.getDiscount());

      customer=new Customer();
      customer.setCustomerId(3L);
      customer.setCart(cart);

        order = new Order();
        order.setOrderId(10L);
        System.out.println("This is the total price "+order.getTotalPrice());
        order.setCustomer(customer);

      orderItem=new OrderItem();
        OrderItemId orderItemId=new OrderItemId(10L,1L);
      orderItem.setOrderItemId(orderItemId);
      orderItem.setProduct(product);
      orderItem.setOrder(order);
      orderItem.setQuantity(10);


    }

    @Test
    public void test_place_order_returns_success() {

        cart.getCartItems().add(cartItem); // ensure cart has items

        when(customerRepository.findById(3L)).thenReturn(Optional.of(customer));
        when(cartRepository.findByCustomer_customerId(3L)).thenReturn(cart);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderItemsRepository.save(any(OrderItem.class))).thenReturn(orderItem);

        double totalCost = businessService.calculateTotalPrice(3L);

        businessService.placeOrder(3L, totalCost);

        assertThat(cart.getCartItems()).isEmpty();

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemsRepository, atLeastOnce()).save(any(OrderItem.class));
        verify(cartRepository, times(1)).save(cart);
    }

}
