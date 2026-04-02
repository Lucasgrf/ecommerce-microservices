package com.lucasgrf.orderservice.application.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponseDTO(
        String id,
        String name,
        String description,
        BigDecimal price,
        String categoryId,
        int stock,
        List<String> images,
        boolean active,
        double weight,
        double width,
        double height,
        double length
) {
}
