package com.uade.tpo.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uade.tpo.marketplace.entity.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserEmail(String email);

}
