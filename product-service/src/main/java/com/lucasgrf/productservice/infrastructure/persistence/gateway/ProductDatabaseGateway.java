package com.lucasgrf.productservice.infrastructure.persistence.gateway;

import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import com.lucasgrf.productservice.infrastructure.persistence.entity.ProductEntity;
import com.lucasgrf.productservice.infrastructure.persistence.mapper.ProductMapper;
import com.lucasgrf.productservice.infrastructure.persistence.repository.JpaProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductDatabaseGateway implements ProductRepository {

    private final JpaProductRepository jpaRepository;

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "#product.id().value()")
    public Product save(Product product) {
        ProductEntity entity = ProductMapper.toEntity(product);
        ProductEntity savedEntity = jpaRepository.save(entity);
        return ProductMapper.toDomain(savedEntity);
    }

    @Override
    @Cacheable(value = "products", key = "#id.value()")
    public Optional<Product> findById(ProductId id) {
        return jpaRepository.findById(java.util.UUID.fromString(id.value()))
                .map(ProductMapper::toDomain);
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "#id.value()")
    public void delete(ProductId id) {
        jpaRepository.deleteById(java.util.UUID.fromString(id.value()));
    }
}
