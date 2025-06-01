package com.uade.tpo.marketplace.service.CartService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.marketplace.entity.*;
import com.uade.tpo.marketplace.entity.dto.*;
import com.uade.tpo.marketplace.exceptions.*;
import com.uade.tpo.marketplace.repository.*;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart removeProductFromCart(String email, Long productId) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        Cart cart = cartRepository.findByUserEmail(email).orElse(null);
        if (cart == null) {
            throw new RuntimeException("Carrito vacio");
        }

        Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
        
        if (cartItem == null){
            throw new RuntimeException("El producto no está en el carrito");
        }
        cartItemRepository.delete(cartItem);
        return cart;
    }

    @Override
    public Cart clearCart(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        Cart cart = cartRepository.findByUserEmail(email).orElse(null);

        cartItemRepository.deleteAll(cart.getItems());

        return cart;
    }

    @Override
    public CartDTO getCartDTOByUserEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        Cart cart = cartRepository.findByUserEmail(email).orElse(null);
        if (cart == null) {
            throw new RuntimeException("Carrito vacio");
        }

        List<CartItemDTO> itemsDTO = cart.getItems().stream().map(item -> {
            Product product = item.getProduct();
            ProductCartDTO productCartDTO = new ProductCartDTO(product.getId(),product.getDescription(), product.getPrice());
            return new CartItemDTO(productCartDTO, item.getQuantity());
        }).toList();

        return new CartDTO(itemsDTO, getTotalPrice(cart));
    }

    private double getTotalPrice(Cart cart) {
        double total = 0;
        for (CartItem item : cart.getItems()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    @Override
    public CartDTO addProductToCartDTO(String email, CartItemRequestDTO cartItemRequestDTO) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }

        Cart cart = cartRepository.findByUserEmail(email).orElse(null);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        Product product = productRepository.findById(cartItemRequestDTO.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existeCartItem = cartItemRepository.findByCartAndProduct(cart, product);
        
        if (existeCartItem == null) {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(cartItemRequestDTO.getQuantity());
            cartItemRepository.save(newCartItem);
        } else {
            existeCartItem.setQuantity(existeCartItem.getQuantity() + cartItemRequestDTO.getQuantity());
            cartItemRepository.save(existeCartItem);
        }

        List<CartItemDTO> itemsDTO = cart.getItems().stream().map(item -> {
            Product productDTO = item.getProduct();
            ProductCartDTO productCartDTO = new ProductCartDTO(productDTO.getId(),productDTO.getDescription(), productDTO.getPrice());
            return new CartItemDTO(productCartDTO, item.getQuantity());
        }).toList();

        double totalPrice = getTotalPrice(cart);
        return new CartDTO(itemsDTO, totalPrice);
    }

    // --- Nuevo método para actualizar cantidad del producto en el carrito ---
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public CartDTO updateCartItemQuantity(String email, CartItemRequestDTO cartItemRequestDTO) throws UserNotFoundException {
    User user = userRepository.findByEmail(email).orElse(null);
    if (user == null) {
        throw new UserNotFoundException();
    }

    if (cartItemRequestDTO.getQuantity() <= 0) {
        throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
    }

    Cart cart = cartRepository.findByUserEmail(email).orElse(null);
    if (cart == null) {
        throw new RuntimeException("Carrito no encontrado para el usuario.");
    }

    Product product = productRepository.findById(cartItemRequestDTO.getProductId())
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
    if (cartItem == null) {
        throw new RuntimeException("El producto no está en el carrito.");
    }

    cartItem.setQuantity(cartItemRequestDTO.getQuantity());
    cartItemRepository.save(cartItem);

    List<CartItemDTO> itemsDTO = cart.getItems().stream().map(item -> {
        Product productDTO = item.getProduct();
        ProductCartDTO productCartDTO = new ProductCartDTO(productDTO.getId(),productDTO.getDescription(), productDTO.getPrice());
        return new CartItemDTO(productCartDTO, item.getQuantity());
    }).toList();

    double totalPrice = getTotalPrice(cart);
    return new CartDTO(itemsDTO, totalPrice);
}
}
