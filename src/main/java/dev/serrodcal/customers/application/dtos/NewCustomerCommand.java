package dev.serrodcal.customers.application.dtos;

public record NewCustomerCommand(
    String name,
    String email
) { }
