package com.uade.tpo.marketplace.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.*;
import com.uade.tpo.marketplace.entity.dto.CategoryRequest;
import com.uade.tpo.marketplace.exceptions.CategoryDuplicateException;
import com.uade.tpo.marketplace.service.CategoryService.CategoryService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;



@RestController
@RequestMapping("categories")
public class CategoriesController {

    @Autowired // anotation para inyectar dependencia, si no tendria que hacer un new CategoryServiceImpl para poder usar los metodos
    private CategoryService categoryService;

    @GetMapping //localhost:8080/categories (GET, me trae todas las categorias)
    public ResponseEntity<List<CategoryRequest>> getCategories(Pageable pageable) {
        return ResponseEntity.ok(categoryService.getCategories(PageRequest.of(0, Integer.MAX_VALUE)));
    }

    @GetMapping("/{categoryId}")    //localhost:8080/categories/path (El path puede ser variable, por eso va entre llaves)
    public ResponseEntity<Category> getCategoryById(@PathVariable long categoryId) {
        Optional<Category> categoryExist = categoryService.getCategoryById(categoryId); // tengo que crear un variable para que me guarde la categoria en caso que exista

        if(categoryExist.isPresent()){
            return ResponseEntity.ok(categoryExist.get());
        }
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping //localhost:8080/categories (POST, me crea una categoria)
    public ResponseEntity<Object> createCategory (@RequestBody Category categoryRequest) throws CategoryDuplicateException{
        Category newCategory = categoryService.createCategory(categoryRequest.getDescription());

        return ResponseEntity
                .created(URI.create("/categories/" + newCategory.getId()))
                .body(newCategory);
    }
    


    
}
