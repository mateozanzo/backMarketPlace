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

public class OrderItem {
    public OrderItem(){
        // Necesito un constructor vacio para que hibernate pueda construir los modelos
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id del carrito

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity; // cantidad de productos que contiene el carrito

    private double price; // precio del producto al momento de la compra

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    
}
