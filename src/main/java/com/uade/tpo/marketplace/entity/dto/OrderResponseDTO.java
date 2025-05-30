package com.uade.tpo.marketplace.entity.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderResponseDTO {
    private Long orderId;
    private LocalDate orderDate;
    private double total;
    private List<OrderItemDTO> items;
    
}
