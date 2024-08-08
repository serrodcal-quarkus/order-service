package dev.serrodcal.customers.infrastructure.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddOrderRequest (
    @NotNull @NotBlank String product,
    @NotNull @Min(0) Integer quantity
){ }
