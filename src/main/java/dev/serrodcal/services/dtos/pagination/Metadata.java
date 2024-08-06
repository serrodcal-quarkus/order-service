package dev.serrodcal.services.dtos.pagination;

public record Metadata(
    Integer page,
    Integer size,
    Integer total
) { }
