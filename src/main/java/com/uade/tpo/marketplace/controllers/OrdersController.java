package com.uade.tpo.marketplace.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.uade.tpo.marketplace.entity.Order;
import com.uade.tpo.marketplace.entity.dto.OrderResponseDTO;
import com.uade.tpo.marketplace.exceptions.CartEmptyException;
import com.uade.tpo.marketplace.exceptions.UserNotFoundException;
import com.uade.tpo.marketplace.service.OrderService.OrdersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/orders")

public class OrdersController {

    @Autowired
    private OrdersService ordersService; // inyecta la dependencia de OrdersService

    @PostMapping()
    public ResponseEntity<OrderResponseDTO> createOrder(Authentication authentication)
            throws CartEmptyException, UserNotFoundException {
        String email = authentication.getName();
        OrderResponseDTO orderResponseDTO = ordersService.createOrder(email);
        return ResponseEntity.ok(orderResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrdersById(Authentication authentication) {
        String email  = authentication.getName();
        List<OrderResponseDTO> ordersDTO = ordersService.getOrdersByEmail(email);
        return ResponseEntity.ok(ordersDTO);
    }

}
