package dev.serrodcal.services.dtos;

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
