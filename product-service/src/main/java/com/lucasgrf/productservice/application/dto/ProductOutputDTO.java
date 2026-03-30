package com.lucasgrf.productservice.application.dto;

import com.lucasgrf.productservice.domain.entity.Product;
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
        double weight,
        double width,
        double height,
        double length,
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
                product.getWeight(),
                product.getWidth(),
                product.getHeight(),
                product.getLength(),
                product.isActive()
        );
    }
}
