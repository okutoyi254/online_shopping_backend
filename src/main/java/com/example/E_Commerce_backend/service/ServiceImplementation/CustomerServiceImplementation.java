package com.example.E_Commerce_backend.service.ServiceImplementation;

import com.example.E_Commerce_backend.dto.AddToCart;
import com.example.E_Commerce_backend.dto.CreateAccountRequest;
import com.example.E_Commerce_backend.entity.Cart;
import com.example.E_Commerce_backend.entity.CartItem;
import com.example.E_Commerce_backend.entity.Customer;
import com.example.E_Commerce_backend.entity.Products;
import com.example.E_Commerce_backend.exceptions.ItemNotFoundException;
import com.example.E_Commerce_backend.exceptions.PhoneNumberAlreadyRegisteredException;
import com.example.E_Commerce_backend.repository.CartItemRepository;
import com.example.E_Commerce_backend.repository.CartRepository;
import com.example.E_Commerce_backend.repository.CustomerRepository;
import com.example.E_Commerce_backend.repository.ProductsRepository;
import com.example.E_Commerce_backend.service.ServiceInterface.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ProductsRepository productsRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    List<Cart> cartList=new ArrayList<>();

    public CustomerServiceImplementation(CustomerRepository customerRepository, ProductsRepository productsRepository) {
        this.customerRepository = customerRepository;
        this.productsRepository = productsRepository;
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

        Cart cart=new Cart();
        cart.setCustomer(customer);

    }

    @Override
    public Page<Products> viewAvailableProducts(Pageable pageable,int categoryId) {
        return productsRepository.getAvailableProducts(pageable,categoryId);
    }

    @Override
    public Page<Products> viewMyRecommendations(Long customerId, Pageable pageable) {
        return null;
    }

    @Override
    public void addProductToCart(AddToCart addToCart) {
        Products products = productsRepository.findByProductId(addToCart.getProductId()).
                orElseThrow(() -> new ItemNotFoundException("Item with the given id does`t exist"));

        Cart cart = cartRepository.findByCustomerId(2L);
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProducts().getProductId().equals(products.getProductId()))
                .findFirst();
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(addToCart.getQuantity());
            cartItem.setTotalPrice(addToCart.getQuantity() * products.getPrice());
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setProducts(products);
            cartItem.setCart(cart);
            cartItem.setQuantity(addToCart.getQuantity());
            cartItem.setUnitPrice(products.getPrice());
            cartItem.setDiscount(0);
            cartItem.setTotalPrice(0D);
            cartItemRepository.save(cartItem);
        }
        cartRepository.save(cart);
    }


    @Override
    public void removeProductFromCart(Long productId) {

    }


}
