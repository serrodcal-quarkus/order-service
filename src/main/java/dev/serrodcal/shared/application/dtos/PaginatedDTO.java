package dev.serrodcal.shared.application.dtos;

public record PaginatedDTO<T>(
    T dto,
    Metadata metadata
) { }
