package com.lucasgrf.productservice.application.dto;

import com.lucasgrf.productservice.domain.entity.Category;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Slug;

public record CategoryOutputDTO(
        String id,
        String name,
        String slug,
        String description
) {
    public static CategoryOutputDTO from(Category category) {
        return new CategoryOutputDTO(
                category.getId().value(),
                category.getName(),
                category.getSlug().value(),
                category.getDescription()
        );
    }
}
