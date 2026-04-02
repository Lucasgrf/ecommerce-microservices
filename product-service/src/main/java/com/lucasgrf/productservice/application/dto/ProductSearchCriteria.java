package com.lucasgrf.productservice.application.dto;

import java.math.BigDecimal;

public record ProductSearchCriteria(
        String query,
        String categoryId,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        int page,
        int size
) {
}
