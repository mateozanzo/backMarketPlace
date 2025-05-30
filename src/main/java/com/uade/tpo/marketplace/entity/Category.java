package com.uade.tpo.marketplace.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data //lombok data genera los getters y setters automaticamente

@Entity // marca que esta clase va a ser persistida en una bd relacional



//esta es la entidad que se guarda en la tabla category
public class Category {

    public Category(){
        // Necesito un constructor vacio para que hibernate pueda construir los modelos
    }

    public Category (String description){
        this.description = description;
    }

    @Id // define pk de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // valor autogenerado para la clave primaria
    private Long id; // id de la categoria

    @Column //indica que el atributo que sigue es una columna en mi bd 
    private String description; // nombre de la categoria

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> product;
    
}
