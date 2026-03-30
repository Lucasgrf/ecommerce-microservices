package com.lucasgrf.productservice.infrastructure.persistence.mapper;

import com.lucasgrf.productservice.domain.entity.Category;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Slug;
import com.lucasgrf.productservice.infrastructure.persistence.mongo.document.CategoryDocument;

public class CategoryMapper {

    public static CategoryDocument toDocument(Category entity) {
        if (entity == null) return null;
        return CategoryDocument.builder()
                .id(entity.getId() != null ? entity.getId().value() : null)
                .name(entity.getName())
                .slug(entity.getSlug() != null ? entity.getSlug().value() : null)
                .description(entity.getDescription())
                .build();
    }

    public static Category toEntity(CategoryDocument document) {
        if (document == null) return null;
        return Category.builder()
                .id(document.getId() != null ? new CategoryId(document.getId()) : null)
                .name(document.getName())
                .slug(document.getSlug() != null ? new Slug(document.getSlug()) : null)
                .description(document.getDescription())
                .build();
    }
}
