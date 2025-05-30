package com.uade.tpo.marketplace.entity.dto;

import lombok.Data;

@Data
public class CategoryRequest {
    private long id;
    private String description;

    public CategoryRequest(Long id, String description){
        this.id = id;
        this.description = description;
    }
}


