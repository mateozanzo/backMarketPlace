package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;

@Data

public class OrderItemDTO {
    private String description;
    private double price;
    private int quantity;
    
    public OrderItemDTO(String description, double price, int quantity) {
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
    
}
