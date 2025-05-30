package com.uade.tpo.marketplace.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;

@Data
@Entity

public class Cart {

    public Cart(){
        // Necesito un constructor vacio para que hibernate pueda construir los modelos
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id del carrito

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; // usuario al que pertenece el carrito
    
    @OneToMany(mappedBy = "cart")
    @JsonManagedReference // Evita la referencia circular entre cart y cartItem
    private List<CartItem> items = new ArrayList<>(); // productos que contiene el carrito

    
}
