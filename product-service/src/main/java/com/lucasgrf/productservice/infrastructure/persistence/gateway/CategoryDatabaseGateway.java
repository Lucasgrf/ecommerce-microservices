package com.lucasgrf.productservice.infrastructure.persistence.gateway;

import com.lucasgrf.productservice.domain.entity.Category;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Slug;
import com.lucasgrf.productservice.infrastructure.persistence.entity.CategoryEntity;
import com.lucasgrf.productservice.infrastructure.persistence.mapper.CategoryMapper;
import com.lucasgrf.productservice.infrastructure.persistence.repository.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryDatabaseGateway implements CategoryRepository {

    private final JpaCategoryRepository jpaRepository;

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public Category save(Category category) {
        CategoryEntity entity = CategoryMapper.toEntity(category);
        CategoryEntity saved = jpaRepository.save(entity);
        return CategoryMapper.toDomain(saved);
    }

    @Override
    public Optional<Category> findById(CategoryId id) {
        return jpaRepository.findById(java.util.UUID.fromString(id.value()))
                .map(CategoryMapper::toDomain);
    }

    @Override
    public Optional<Category> findBySlug(Slug slug) {
        return jpaRepository.findBySlug(slug.value())
                .map(CategoryMapper::toDomain);
    }

    @Override
    public boolean existsBySlug(Slug slug) {
        return jpaRepository.findBySlug(slug.value()).isPresent();
    }

    @Override
    @Cacheable(value = "categories")
    public List<Category> findAll() {
        return jpaRepository.findAll().stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "categories", allEntries = true)
    public void deleteById(CategoryId id) {
        jpaRepository.deleteById(java.util.UUID.fromString(id.value()));
    }
}
