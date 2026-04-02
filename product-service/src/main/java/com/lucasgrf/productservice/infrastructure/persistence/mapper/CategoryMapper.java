package com.lucasgrf.productservice.infrastructure.persistence.mapper;

import com.lucasgrf.productservice.domain.entity.Category;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Slug;
import com.lucasgrf.productservice.infrastructure.persistence.entity.CategoryEntity;

public class CategoryMapper {

    public static CategoryEntity toEntity(Category domain) {
        if (domain == null) return null;
        return CategoryEntity.builder()
                .id(domain.getId() != null ? java.util.UUID.fromString(domain.getId().value()) : null)
                .name(domain.getName())
                .slug(domain.getSlug() != null ? domain.getSlug().value() : null)
                .description(domain.getDescription())
                .build();
    }

    public static Category toDomain(CategoryEntity entity) {
        if (entity == null) return null;
        return Category.builder()
                .id(entity.getId() != null ? new CategoryId(entity.getId().toString()) : null)
                .name(entity.getName())
                .slug(entity.getSlug() != null ? new Slug(entity.getSlug()) : null)
                .description(entity.getDescription())
                .build();
    }
}
