package com.lucasgrf.productservice.application.dto;

import java.math.BigDecimal;

public record UpdateProductInputDTO(
        String id,
        String categoryId,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity
) {
}
