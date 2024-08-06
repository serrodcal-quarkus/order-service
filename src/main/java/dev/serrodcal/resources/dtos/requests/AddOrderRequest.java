package dev.serrodcal.resources.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddOrderRequest (
    @NotNull @NotBlank String product,
    @NotNull @Min(0) Integer quantity
){ }
