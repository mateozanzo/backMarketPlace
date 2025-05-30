package com.uade.tpo.marketplace.service.CategoryService;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.dto.*;
import com.uade.tpo.marketplace.exceptions.CategoryDuplicateException;

public interface CategoryService {
    // Quiero una interfaz para declarar los metodos
    // todos los servicios tienen su interfaz y una implementacion

    public List<CategoryRequest> getCategories(PageRequest pageable);

    public Optional<Category> getCategoryById(Long categoryId);

    public Category createCategory(String description) throws CategoryDuplicateException;

}