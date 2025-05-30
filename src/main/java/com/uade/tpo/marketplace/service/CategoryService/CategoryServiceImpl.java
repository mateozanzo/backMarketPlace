package com.uade.tpo.marketplace.service.CategoryService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.dto.CategoryRequest;
import com.uade.tpo.marketplace.exceptions.CategoryDuplicateException;
import com.uade.tpo.marketplace.repository.CategoryRepository;


@Service // le tengo que indicar a spring que esto es un servicio, el beans viene a buscar aca las dependencias
public class CategoryServiceImpl implements CategoryService {

    @Autowired 
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryRequest> getCategories(PageRequest pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map (category -> new CategoryRequest(category.getId(), category.getDescription())).toList();
    }

    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Category createCategory(String description) throws CategoryDuplicateException {
        List<Category> categories = categoryRepository.findByDescription(description);
        if (categories.isEmpty()){
            return categoryRepository.save(new Category(description));
        }
        throw new CategoryDuplicateException();
    }
    
}
