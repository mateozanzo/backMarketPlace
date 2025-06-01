package com.uade.tpo.marketplace.service.ProductService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.dto.CategoryRequest;
import com.uade.tpo.marketplace.entity.dto.ProductDTO;
import com.uade.tpo.marketplace.exceptions.ProductDuplicateException;
import com.uade.tpo.marketplace.repository.ProductRepository;
import com.uade.tpo.marketplace.service.CategoryService.CategoryService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Product createProduct(String name, String description, double price, int stock, Long categoryId, MultipartFile image) throws ProductDuplicateException {

        Category categoria = categoryService.getCategoryById(categoryId).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(categoria);

        try{
            product.setImage(image.getBytes());
        }catch (IOException e) {
            throw new RuntimeException(e);
        }


        return productRepository.save(product);
    }

    @Override
    public List<ProductDTO> getProducts(PageRequest pageable) {
        Page<Product> productPage = productRepository.findAllWithStock(pageable);

        return productPage.map(product -> new ProductDTO(
            product.getId(),
            product.getName(),
            product.getDescription(), 
            product.getPrice(), 
            product.getStock(), 
            product.getCategory().getDescription(), 
            product.getImage() != null ? Base64.getEncoder().encodeToString(product.getImage()) : null ))
            .toList();
    }

    @Override
    public List<ProductDTO> getProductByCategoryId(Long productId) {
        List<Product> productos =  productRepository.findByCategoryId(productId);
        return productos.stream().map(producto -> new ProductDTO(
            producto.getId(),
            producto.getName(),
            producto.getDescription(),
            producto.getPrice(),
            producto.getStock(),
            producto.getCategory().getDescription(),
            producto.getImage() != null ? Base64.getEncoder().encodeToString(producto.getImage()) : null))
            .toList();
    }

    @Override
    public void updateStock(Long productId, int newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));

        product.setStock(newStock);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Producto no encontrado con ID: " + productId);
        }
        productRepository.deleteById(productId);
    }
}
