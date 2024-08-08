package dev.serrodcal.shared.infrastructure.dtos;

public record PaginatedResponse<T>(
    T payload,
    Metadata metadata
) { }
