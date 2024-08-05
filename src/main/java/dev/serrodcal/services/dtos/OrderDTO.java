package dev.serrodcal.services.dtos;

import java.time.LocalDateTime;

public record OrderDTO(
        Long id,
        String product,
        Integer quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
