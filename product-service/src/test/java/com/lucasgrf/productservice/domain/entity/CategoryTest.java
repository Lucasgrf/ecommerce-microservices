package com.lucasgrf.productservice.domain.entity;

import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Slug;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void shouldCreateCategorySuccessfully() {
        Category category = Category.builder()
                .id(CategoryId.generate())
                .name("Tênis Esportivos")
                .description("Calçados para corrida e academia")
                .slug(Slug.fromText("Tênis Esportivos"))
                .build();

        assertNotNull(category.getId());
        assertEquals("Tênis Esportivos", category.getName());
        assertEquals("Calçados para corrida e academia", category.getDescription());
        assertEquals("tenis-esportivos", category.getSlug().value());
    }

    @Test
    void shouldUpdateCategoryNameAndSlug() {
        Category category = Category.builder()
                .id(CategoryId.generate())
                .name("Roupas")
                .slug(Slug.fromText("Roupas"))
                .build();

        category.updateName("Roupas Masculinas");
        assertEquals("Roupas Masculinas", category.getName());
        assertEquals("roupas-masculinas", category.getSlug().value());
    }
}
