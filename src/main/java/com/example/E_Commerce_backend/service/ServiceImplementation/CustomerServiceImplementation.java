package com.example.E_Commerce_backend.service.ServiceImplementation;

import com.example.E_Commerce_backend.dto.AddToCart;
import com.example.E_Commerce_backend.dto.CreateAccountRequest;
import com.example.E_Commerce_backend.entity.*;
import com.example.E_Commerce_backend.exceptions.ItemNotFoundException;
import com.example.E_Commerce_backend.exceptions.PhoneNumberAlreadyRegisteredException;
import com.example.E_Commerce_backend.repository.*;
import com.example.E_Commerce_backend.service.ServiceInterface.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ProductsRepository productsRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public CustomerServiceImplementation(CustomerRepository customerRepository, ProductsRepository productsRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.productsRepository = productsRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }


    public void createAccount(@Valid @RequestBody CreateAccountRequest createAccount){
        if(customerRepository.existsByEmail(createAccount.getEmail())||
                customerRepository.existsByPhoneNumber(createAccount.getPhoneNumber())){
            throw new PhoneNumberAlreadyRegisteredException("Phone number already exists!");

        }

        User user=new User();
        user.setUsername(createAccount.getEmail());
        user.setRole("CUSTOMER");
        user.setPassword(createAccount.getPassword());

        Customer customer=new Customer();
        customer.setEmail(createAccount.getEmail());
        customer.setPassword(createAccount.getPassword());
        customer.setFirstName(createAccount.getFirstName());
        customer.setLastName(createAccount.getLastName());
        customer.setPhoneNumber(createAccount.getPhoneNumber());
        customer.setHomeAddress(createAccount.getHomeAddress());
        customer.setUser(user);
        customerRepository.save(customer);



        Cart cart=new Cart();
        cart.setCustomer(customer);

    }

    @Override
    public Page<Product> viewAvailableProducts(Pageable pageable, int categoryId) {
        return null;
    }

    @Override
    public Page<Product> viewMyRecommendations(Long customerId, Pageable pageable) {
        //Recommendations implementation
        return  null;
    }

    @Override
    public void addProductToCart(AddToCart addToCart) {
        Product product = productsRepository.findByProductId(addToCart.getProductId()).
                orElseThrow(() -> new ItemNotFoundException("Item with the given id does`t exist"));

        Cart cart = cartRepository.findByCustomer_customerId(2L);
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(product.getProductId()))
                .findFirst();
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(addToCart.getQuantity());
            cartItem.setTotalPrice(addToCart.getQuantity() * product.getPrice());
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(addToCart.getQuantity());
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setDiscount(0);
            cartItem.setTotalPrice(0D);
            cartItemRepository.save(cartItem);
        }
        cartRepository.save(cart);
    }


    @Override
    public void removeProductFromCart(Long productId) {
        Cart cart=cartRepository.findByCustomer_customerId(2L);
       CartItem cartItem=cartItemRepository.findByProduct_productId(productId);
       cart.getCartItems().remove(cartItem);
       cartRepository.save(cart);
    }


}
