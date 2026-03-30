package com.lucasgrf.orderservice.application.dto;

import java.math.BigDecimal;

public record OrderItemOutputDTO(
        String productId,
        int quantity,
        BigDecimal price,
        BigDecimal total
) {
}
