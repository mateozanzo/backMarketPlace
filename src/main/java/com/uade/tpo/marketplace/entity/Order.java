package com.uade.tpo.marketplace.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity

@Table(name = "orders") //cambiar nombre a mi table
public class Order {

    public Order(){
        // Necesito un constructor vacio para que hibernate pueda construir los modelos
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private double total;

    @Column(name = "order_date")
    private LocalDate orderDate; // fecha de la orden

    @ManyToOne
    @JoinColumn(name = "user_id", // Genera tabla intermedia, entre usuarios y ordenes 
                nullable = false) // simula un SELECT * FROM order JOIN user ON user_id 
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Evita la referencia circular entre cart y cartItem
    private List<OrderItem> orderItems = new ArrayList<>(); // productos que contiene el carrito
}
