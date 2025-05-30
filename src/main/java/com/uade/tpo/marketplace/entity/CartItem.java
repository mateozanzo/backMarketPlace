package com.uade.tpo.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity



public class CartItem {

    public CartItem(){
        // Necesito un constructor vacio para que hibernate pueda construir los modelos
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id del carrito

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity; // cantidad de productos que contiene el carrito

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference // Evita la referencia circular entre cart y cartItem
    private Cart cart; // carrito al que pertenece el producto
    
}
