package dev.serrodcal.customers.infrastructure.dtos;

import dev.serrodcal.orders.infrastructure.dtos.OrderResponse;

import java.time.LocalDateTime;
import java.util.List;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        List<OrderResponse> orders,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
