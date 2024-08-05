package dev.serrodcal.resources.dtos.requests;

public record NewCustomerRequest(
        String name,
        String email
) { }
