package com.uade.tpo.marketplace.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.uade.tpo.marketplace.entity.*;
import com.uade.tpo.marketplace.entity.dto.ProductDTO;
import com.uade.tpo.marketplace.entity.dto.UpdateStockRequest;
import com.uade.tpo.marketplace.exceptions.ProductDuplicateException;
import com.uade.tpo.marketplace.service.CategoryService.CategoryService;
import com.uade.tpo.marketplace.service.ProductService.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/products")

public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createProduct(@RequestParam String name, @RequestParam String description, @RequestParam double price, @RequestParam int stock, 
    @RequestParam Long categoryId, @RequestParam("image") MultipartFile image) throws ProductDuplicateException {
        Product newProduct = productService.createProduct(name, description, price, stock, categoryId, image);

        return ResponseEntity
                .created(URI.create("/products/" + newProduct.getId()))
                .body(newProduct);

    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getProducts(PageRequest.of(0, Integer.MAX_VALUE)));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductById(@PathVariable Long categoryId) {
        List<ProductDTO> productos = productService.getProductByCategoryId(categoryId);
        return ResponseEntity.ok(productos);

    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestBody UpdateStockRequest request) {
        productService.updateStock(id, request.getStock());
        return ResponseEntity.ok("Stock actualizado correctamente");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }

}
