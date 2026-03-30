package com.lucasgrf.productservice.application.dto;

import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Money;
import com.lucasgrf.productservice.domain.valueobject.ProductId;

import java.math.BigDecimal;
import java.util.List;

public record ProductOutputDTO(
        String id,
        String name,
        String description,
        BigDecimal price,
        String categoryId,
        int stock,
        List<String> images,
        List<String> variants,
        boolean active
) {
    public static ProductOutputDTO from(Product product) {
        return new ProductOutputDTO(
                product.getId().value(),
                product.getName(),
                product.getDescription(),
                product.getPrice().amount(),
                product.getCategoryId() != null ? product.getCategoryId().value() : null,
                product.getStock(),
                product.getImages(),
                product.getVariants(),
                product.isActive()
        );
    }
}
