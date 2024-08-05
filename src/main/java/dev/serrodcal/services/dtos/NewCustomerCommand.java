package dev.serrodcal.services.dtos;

public record NewCustomerCommand(
    String name,
    String email
) { }
