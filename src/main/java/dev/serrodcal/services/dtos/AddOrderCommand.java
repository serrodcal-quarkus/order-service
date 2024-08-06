package dev.serrodcal.services.dtos;

public record AddOrderCommand(
        String product,
        Integer quantity
) {
}
