package com.uade.tpo.marketplace.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    @Query(value =  " SELECT p FROM Product p WHERE p.description  = ?1 ")
    List<Product> findByDescription(String description);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    Page<Product> findAllWithStock(Pageable pageable);
} 

