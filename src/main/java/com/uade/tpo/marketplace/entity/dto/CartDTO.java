package com.uade.tpo.marketplace.entity.dto;

import java.util.List;

import lombok.Data;

@Data
public class CartDTO {
    private List<CartItemDTO> items; // productos que contiene el carrito
    private double totalPrice; // precio total del carrito

    public CartDTO(List<CartItemDTO> items, double totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }
    
}
