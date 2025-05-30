package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;

@Data
public class CartItemDTO {

    private ProductCartDTO product; // producto que contiene el carrito
    private int quantity; // cantidad de productos que contiene el carrito

    public CartItemDTO(ProductCartDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
}
