package com.lucasgrf.productservice.application.dto;

public record UpdateCategoryInputDTO(
        String id,
        String name,
        String description
) {
}
