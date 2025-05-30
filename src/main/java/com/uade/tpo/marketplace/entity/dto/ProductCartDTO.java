package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;

@Data
public class ProductCartDTO {
    private String description;
    private double price;

    public ProductCartDTO(String description, double price) {
        this.description = description; 
        this.price = price;
    }

}
