package dev.serrodcal.customers.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewCustomerRequest(
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String email
) { }
