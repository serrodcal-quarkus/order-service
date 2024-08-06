package dev.serrodcal.resources.dtos.responses.pagination;

public record PaginatedResponse<T>(
    T payload,
    Metadata metadata
) { }
