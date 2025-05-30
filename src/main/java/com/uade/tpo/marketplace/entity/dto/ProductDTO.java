package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;

@Data

public class ProductDTO {
    private String name;
    private String description;
    private double price;
    private int stock;
    private String categoryName;
    private String imageBase64;

    public ProductDTO(String name, String description, double price, int stock, String categoryName,String imageBase64){
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categoryName = categoryName;
        this.imageBase64 = imageBase64;
    }
}
