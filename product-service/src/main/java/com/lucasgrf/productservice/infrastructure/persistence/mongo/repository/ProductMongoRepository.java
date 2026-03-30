package com.lucasgrf.productservice.infrastructure.persistence.mongo.repository;

import com.lucasgrf.productservice.infrastructure.persistence.mongo.document.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMongoRepository extends MongoRepository<ProductDocument, String> {
}
