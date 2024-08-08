package dev.serrodcal.customers.application.dtos;

public record AddOrderCommand(
        String product,
        Integer quantity
) {
}
