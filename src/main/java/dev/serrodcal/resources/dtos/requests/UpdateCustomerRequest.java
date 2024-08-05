package dev.serrodcal.resources.dtos.requests;

public record UpdateCustomerRequest(
        String name,
        String email
) { }
