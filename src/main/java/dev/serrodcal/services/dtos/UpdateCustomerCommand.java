package dev.serrodcal.services.dtos;

public record UpdateCustomerCommand(
        String name,
        String email
) { }
