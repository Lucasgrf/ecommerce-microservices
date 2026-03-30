package com.lucasgrf.productservice.infrastructure.persistence.mongo.repository;

import com.lucasgrf.productservice.infrastructure.persistence.mongo.document.CategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryMongoRepository extends MongoRepository<CategoryDocument, String> {
    Optional<CategoryDocument> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
