package dev.serrodcal.services.dtos.pagination;

public record PaginatedDTO<T>(
    T dto,
    Metadata metadata
) { }
