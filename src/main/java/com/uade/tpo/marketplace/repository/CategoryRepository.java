package com.uade.tpo.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.Category;

import java.util.List;


@Repository

public interface CategoryRepository extends JpaRepository<Category,Long> { // JpaRepository <Entidad,tipo de dato primario>
    @Query(value =  " SELECT c FROM Category c WHERE c.description  = ?1 ")
    List<Category> findByDescription(String description);
}