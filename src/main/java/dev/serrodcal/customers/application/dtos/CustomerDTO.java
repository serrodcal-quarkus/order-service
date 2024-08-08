package dev.serrodcal.customers.application.dtos;

import dev.serrodcal.orders.application.dtos.OrderDTO;

import java.time.LocalDateTime;
import java.util.List;

public record CustomerDTO(
        Long id,
        String name,
        String email,
        List<OrderDTO> orders,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
