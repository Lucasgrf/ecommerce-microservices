package com.lucasgrf.productservice.infrastructure.persistence.gateway;

import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import com.lucasgrf.productservice.infrastructure.persistence.mapper.ProductMapper;
import com.lucasgrf.productservice.infrastructure.persistence.mongo.document.ProductDocument;
import com.lucasgrf.productservice.infrastructure.persistence.elasticsearch.document.ProductIndex;
import com.lucasgrf.productservice.infrastructure.persistence.mongo.repository.ProductMongoRepository;
import com.lucasgrf.productservice.infrastructure.persistence.elasticsearch.repository.ProductElasticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductDatabaseGateway implements ProductRepository {

    private final ProductMongoRepository mongoRepository;
    private final ProductElasticRepository elasticRepository;

    @Override
    @Transactional
    public Product save(Product product) {
        // Save in Mongo
        ProductDocument document = ProductMapper.toDocument(product);
        ProductDocument savedDocument = mongoRepository.save(document);
        
        // Sync with ElasticSearch
        ProductIndex index = ProductMapper.toIndex(product);
        elasticRepository.save(index);
        
        return ProductMapper.toEntity(savedDocument);
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return mongoRepository.findById(id.value())
                .map(ProductMapper::toEntity);
    }

    @Override
    @Transactional
    public void delete(ProductId id) {
        mongoRepository.deleteById(id.value());
        elasticRepository.deleteById(id.value());
    }
}
