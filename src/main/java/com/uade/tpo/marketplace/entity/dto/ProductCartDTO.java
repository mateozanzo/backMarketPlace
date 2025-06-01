package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;

@Data
public class ProductCartDTO {
    private Long id;
    private String description;
    private double price;

    public ProductCartDTO(Long id, String description, double price) {
        this.id = id;
        this.description = description; 
        this.price = price;
    }

}
