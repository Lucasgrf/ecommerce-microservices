package com.lucasgrf.productservice.infrastructure.persistence.gateway;

import com.lucasgrf.productservice.domain.entity.Category;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Slug;
import com.lucasgrf.productservice.infrastructure.persistence.mapper.CategoryMapper;
import com.lucasgrf.productservice.infrastructure.persistence.mongo.document.CategoryDocument;
import com.lucasgrf.productservice.infrastructure.persistence.mongo.repository.CategoryMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryDatabaseGateway implements CategoryRepository {

    private final CategoryMongoRepository mongoRepository;

    @Override
    public Category save(Category category) {
        CategoryDocument document = CategoryMapper.toDocument(category);
        CategoryDocument saved = mongoRepository.save(document);
        return CategoryMapper.toEntity(saved);
    }

    @Override
    public Optional<Category> findById(CategoryId id) {
        return mongoRepository.findById(id.value())
                .map(CategoryMapper::toEntity);
    }

    @Override
    public Optional<Category> findBySlug(Slug slug) {
        return mongoRepository.findBySlug(slug.value())
                .map(CategoryMapper::toEntity);
    }

    @Override
    public boolean existsBySlug(Slug slug) {
        return mongoRepository.existsBySlug(slug.value());
    }

    @Override
    public List<Category> findAll() {
        return mongoRepository.findAll().stream()
                .map(CategoryMapper::toEntity)
                .collect(Collectors.toList());
    }
}
