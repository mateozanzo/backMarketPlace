package com.uade.tpo.marketplace.service.ProductService;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.dto.ProductDTO;
import com.uade.tpo.marketplace.exceptions.ProductDuplicateException;

public interface ProductService {
    public Product createProduct(String name, String description, double price, int stock, Long categoryId, MultipartFile image) throws ProductDuplicateException;

    public List<ProductDTO> getProducts(PageRequest pageable);

    public List<ProductDTO> getProductByCategoryId(Long categoryId);

    public void updateStock(Long productId, int newStock);

    public void deleteProduct(Long productId);
}
