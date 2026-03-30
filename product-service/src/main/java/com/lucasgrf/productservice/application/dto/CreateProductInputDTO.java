package com.lucasgrf.productservice.application.dto;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductInputDTO(
        String name,
        String description,
        BigDecimal price,
        String categoryId,
        int stock,
        List<String> images,
        List<String> variants
) {
}
