package dev.serrodcal.resources.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewCustomerRequest(
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String email
) { }
