package com.uade.tpo.marketplace.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uade.tpo.marketplace.entity.dto.CategoryRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    
    @Column
    private String description;

    @Column
    private double price;

    @Column
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonBackReference
    private Category category;

    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;
}
