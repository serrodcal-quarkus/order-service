package dev.serrodcal.customers.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCustomerRequest(
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String email
) { }
