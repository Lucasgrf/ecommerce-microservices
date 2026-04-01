package com.lucasgrf.productservice.domain.repository;

import com.lucasgrf.productservice.domain.entity.Category;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Slug;

import java.util.Optional;
import java.util.List;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(CategoryId id);
    Optional<Category> findBySlug(Slug slug);
    boolean existsBySlug(Slug slug);
    List<Category> findAll();
    void deleteById(CategoryId id);
}
