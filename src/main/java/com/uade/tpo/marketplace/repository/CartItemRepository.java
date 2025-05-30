package com.uade.tpo.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.Cart;
import com.uade.tpo.marketplace.entity.CartItem;
import com.uade.tpo.marketplace.entity.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Aquí puedes agregar métodos personalizados si es necesario
    
    CartItem findByCartAndProduct(Cart cart, Product product);
    List<CartItem> findByCart(Cart cart);
    
}
