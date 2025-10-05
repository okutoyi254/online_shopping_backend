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


    public Customer createAccount(@Valid @RequestBody CreateAccountRequest createAccount){
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



        Cart cart=new Cart();
        cart.setCustomer(customer);
        cartRepository.save(cart);

        return customerRepository.save(customer);

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
    public CartItem addProductToCart(AddToCart addToCart) {
        // 1. Validate product
        Product product = productsRepository.findByProductId(addToCart.getProductId())
                .orElseThrow(() -> new ItemNotFoundException("Item with the given id does not exist"));

        // 2. Get customer’s cart (hardcoded 2L for now, better to pass customerId)
        Cart cart = cartRepository.findByCustomer_customerId(2L);

        // 3. Check if product already in cart
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(product.getProductId()))
                .findFirst();

        CartItem cartItem;

        if (existingItem.isPresent()) {
            // Update existing
            cartItem = existingItem.get();
            cartItem.setQuantity(addToCart.getQuantity());
            cartItem.setTotalPrice(addToCart.getQuantity() * product.getPrice());
        } else {
            // Create new
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(addToCart.getQuantity());
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setDiscount(0);
            cartItem.setTotalPrice(addToCart.getQuantity() * product.getPrice());

            cartItemRepository.save(cartItem);
            cart.getCartItems().add(cartItem); // ensure cart relationship is updated
        }

        // 4. Save the cart (to maintain consistency)
        cartRepository.save(cart);

        // 5. Return the updated/created cartItem
        return cartItem;
    }


    @Override
    public void removeProductFromCart(Long productId) {
        Cart cart=cartRepository.findByCustomer_customerId(2L);
       CartItem cartItem=cartItemRepository.findByProduct_productId(productId);
       cart.getCartItems().remove(cartItem);
       cartRepository.save(cart);
    }


}
