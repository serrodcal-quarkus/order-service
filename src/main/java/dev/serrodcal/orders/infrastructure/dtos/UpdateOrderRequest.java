package dev.serrodcal.orders.infrastructure.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderRequest(
        @NotNull @NotBlank String product,
        @NotNull @Min(0) Integer quantity
) { }
