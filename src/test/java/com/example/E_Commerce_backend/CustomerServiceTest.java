package com.example.E_Commerce_backend;

import com.example.E_Commerce_backend.dto.AddToCart;
import com.example.E_Commerce_backend.dto.CartItemId;
import com.example.E_Commerce_backend.dto.CreateAccountRequest;
import com.example.E_Commerce_backend.entity.*;
import com.example.E_Commerce_backend.repository.CartItemRepository;
import com.example.E_Commerce_backend.repository.CartRepository;
import com.example.E_Commerce_backend.repository.CustomerRepository;
import com.example.E_Commerce_backend.repository.ProductsRepository;
import com.example.E_Commerce_backend.service.ServiceImplementation.CustomerServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CartRepository cartRepository;


    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private CustomerServiceImplementation customerService;

    private Product product;
    private Cart cart;

    @BeforeEach
    void setUp(){

        product=new Product();
        product.setProductId(1L);
        product.setPrice(100.0);

        cart=new Cart();
        cart.setCartId(10L);
        cart.setCartItems(new ArrayList<>());

    }

    @Test
    void test_add_customer_returns_true(){

        CreateAccountRequest createAccountRequest=new CreateAccountRequest("James","Okutoyi","21 Soy","0707636849","okutoyi@gmail.com","2036");

        User user=new User(1L,"0707636849","2036","CUSTOMER");
        Customer mockCustomer=new Customer();
        mockCustomer.setCustomerId(1L);
        mockCustomer.setEmail("okutoyi@gmail.com");
        mockCustomer.setLastName("Okutoyi");
        mockCustomer.setPassword("2036");
        mockCustomer.setPhoneNumber("0707636849");
        mockCustomer.setFirstName("James");
        mockCustomer.setHomeAddress("21 Soy");
        mockCustomer.setUser(user);


        when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);

        Customer saveCustomer=customerService.createAccount(createAccountRequest);

        assertEquals("James",saveCustomer.getFirstName());

        //verify(customerRepository,times(1)).save(mockCustomer);
    }

    @Test
    void test_add_product_to_cart_returns_success(){

        AddToCart request=new AddToCart(1L,2);

        when(productsRepository.findByProductId(1L)).thenReturn(Optional.of(product));
        when(cartRepository.findByCustomer_customerId(2L)).thenReturn(cart);

        customerService.addProductToCart(request);

        verify(cartItemRepository,times(1)).save(any(CartItem.class));
        verify(cartRepository,times(1)).save(cart);

        assertEquals(1,cart.getCartItems().size());
        CartItem item=cart.getCartItems().get(0);
        assertEquals(2,item.getQuantity());
        assertEquals(100.0,item.getUnitPrice());

    }

    @Test
    void test_add_product_to_cart_updates_already_existing_product(){
        AddToCart request=new AddToCart(1L,20);

        CartItem existingItem=new CartItem();
        existingItem.setProduct(product);
        existingItem.setUnitPrice(100.0D);
        existingItem.setQuantity(12);

        cart.getCartItems().add(existingItem);

        when(productsRepository.findByProductId(1L)).thenReturn(Optional.of(product));
        when(cartRepository.findByCustomer_customerId(2L)).thenReturn(cart);

        customerService.addProductToCart(request);

        verify(cartItemRepository,never()).save(any(CartItem.class));
        verify(cartRepository,times(1)).save(cart);

        CartItem exists=cart.getCartItems().get(0);

        assertEquals(20,exists.getQuantity());
        assertEquals(100.0,existingItem.getUnitPrice());
    }

    @Test
    void test_delete_product_from_cart_returns_success() {
        // Arrange
        CartItemId cartItemId=new CartItemId(2L,1L);
        CartItem item = new CartItem();
        item.setCartItemId(cartItemId);
        item.setProduct(product);
        cart.getCartItems().add(item);

        when(cartRepository.findByCustomer_customerId(2L)).thenReturn(cart);
        when(productsRepository.findByProductId(1L)).thenReturn(Optional.of(product));

        // Act
        customerService.removeProductFromCart(1L);

        assertFalse(cart.getCartItems().contains(item));
        // Assert
        verify(cartRepository,times(1)).save(cart);
    }

}
