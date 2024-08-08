package dev.serrodcal.customers.application.dtos;

public record UpdateCustomerCommand(
        String name,
        String email
) { }
