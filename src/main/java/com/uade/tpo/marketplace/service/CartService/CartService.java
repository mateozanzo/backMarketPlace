package com.uade.tpo.marketplace.service.CartService;

import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.entity.dto.CartDTO;
import com.uade.tpo.marketplace.entity.dto.CartItemRequestDTO;
import com.uade.tpo.marketplace.exceptions.UserNotFoundException;

public interface CartService {

    // Quiero una interfaz para declarar los metodos
    // todos los servicios tienen su interfaz y una implementacion
    public Cart removeProductFromCart(String email, Long productId) throws UserNotFoundException;
    public Cart clearCart(String email) throws UserNotFoundException;
    public CartDTO getCartDTOByUserEmail(String email) throws UserNotFoundException;
    public CartDTO addProductToCartDTO(String email, CartItemRequestDTO cartItemRequestDTO) throws UserNotFoundException;
    CartDTO updateCartItemQuantity(String email, CartItemRequestDTO cartItemRequestDTO) throws UserNotFoundException;


}
