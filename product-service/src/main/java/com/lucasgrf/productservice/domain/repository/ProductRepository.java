package com.lucasgrf.productservice.domain.repository;

import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.valueobject.ProductId;

import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(ProductId id);
    void delete(ProductId id);
}
