package dev.serrodcal.orders.infrastructure.dtos;

import java.time.LocalDateTime;

public record OrderResponse(
    Long id,
    String product,
    Integer quantity,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) { }
