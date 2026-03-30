package com.lucasgrf.productservice.infrastructure.persistence.mapper;

import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Money;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import com.lucasgrf.productservice.infrastructure.persistence.elasticsearch.document.ProductIndex;
import com.lucasgrf.productservice.infrastructure.persistence.mongo.document.ProductDocument;

public class ProductMapper {

    public static ProductDocument toDocument(Product entity) {
        if (entity == null) return null;
        return ProductDocument.builder()
                .id(entity.getId() != null ? entity.getId().value() : null)
                .categoryId(entity.getCategoryId() != null ? entity.getCategoryId().value() : null)
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice() != null ? entity.getPrice().amount() : null)
                .stock(entity.getStock())
                .images(entity.getImages())
                .variants(entity.getVariants())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static Product toEntity(ProductDocument document) {
        if (document == null) return null;
        return Product.builder()
                .id(document.getId() != null ? new ProductId(document.getId()) : null)
                .categoryId(document.getCategoryId() != null ? new CategoryId(document.getCategoryId()) : null)
                .name(document.getName())
                .description(document.getDescription())
                .price(document.getPrice() != null ? new Money(document.getPrice()) : null)
                .stock(document.getStock())
                .images(document.getImages())
                .variants(document.getVariants())
                .active(document.isActive())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .build();
    }

    public static ProductIndex toIndex(Product entity) {
        if (entity == null) return null;
        return ProductIndex.builder()
                .id(entity.getId() != null ? entity.getId().value() : null)
                .categoryId(entity.getCategoryId() != null ? entity.getCategoryId().value() : null)
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice() != null ? entity.getPrice().amount() : null)
                .stock(entity.getStock())
                .images(entity.getImages())
                .active(entity.isActive())
                .build();
    }
}
