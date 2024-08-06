package dev.serrodcal.resources.dtos.responses.pagination;

public record Metadata(
    Integer page,
    Integer size,
    Integer total
) { }
