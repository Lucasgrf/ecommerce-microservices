package com.lucasgrf.productservice.infrastructure.persistence.mapper;

import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Money;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import com.lucasgrf.productservice.infrastructure.persistence.entity.ProductEntity;

public class ProductMapper {

    public static ProductEntity toEntity(Product domain) {
        if (domain == null) return null;
        return ProductEntity.builder()
                .id(domain.getId() != null ? java.util.UUID.fromString(domain.getId().value()) : null)
                .categoryId(domain.getCategoryId() != null ? java.util.UUID.fromString(domain.getCategoryId().value()) : null)
                .name(domain.getName())
                .description(domain.getDescription())
                .price(domain.getPrice() != null ? domain.getPrice().amount() : null)
                .stock(domain.getStock())
                .images(domain.getImages())
                .variants(domain.getVariants())
                .weight(domain.getWeight())
                .width(domain.getWidth())
                .height(domain.getHeight())
                .length(domain.getLength())
                .active(domain.isActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public static Product toDomain(ProductEntity entity) {
        if (entity == null) return null;
        return Product.builder()
                .id(entity.getId() != null ? new ProductId(entity.getId().toString()) : null)
                .categoryId(entity.getCategoryId() != null ? new CategoryId(entity.getCategoryId().toString()) : null)
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice() != null ? new Money(entity.getPrice()) : null)
                .stock(entity.getStock())
                .images(entity.getImages())
                .variants(entity.getVariants())
                .weight(entity.getWeight())
                .width(entity.getWidth())
                .height(entity.getHeight())
                .length(entity.getLength())
                .active(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
