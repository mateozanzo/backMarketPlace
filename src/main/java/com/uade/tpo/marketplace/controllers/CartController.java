package com.uade.tpo.marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.entity.dto.CartDTO;
import com.uade.tpo.marketplace.entity.dto.CartItemRequestDTO;
import com.uade.tpo.marketplace.exceptions.UserNotFoundException;
import com.uade.tpo.marketplace.service.CartService.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<CartDTO> getCartByUserMail(Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        CartDTO cartDTO = cartService.getCartDTOByUserEmail(email);
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping
    public ResponseEntity<CartDTO> addItemToCart(@RequestBody CartItemRequestDTO cartItemRequestDTO, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        CartDTO cartDTO = cartService.addProductToCartDTO(email, cartItemRequestDTO);
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long productId, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        cartService.removeProductFromCart(email, productId);
        return ResponseEntity.ok("Producto eliminado del carrito correctamente");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        cartService.clearCart(email);
        return ResponseEntity.ok("Carrito vaciado correctamente");
    }

    @PutMapping("/update")
    public ResponseEntity<CartDTO> updateCartItemQuantity(@RequestBody CartItemRequestDTO request, Authentication authentication) {
        String email = authentication.getName();

        try {
            CartDTO updatedCart = cartService.updateCartItemQuantity(email, request);
            return ResponseEntity.ok(updatedCart);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
