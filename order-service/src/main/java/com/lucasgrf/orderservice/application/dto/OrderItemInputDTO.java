package com.lucasgrf.orderservice.application.dto;

import java.math.BigDecimal;

public record OrderItemInputDTO(
        String productId,
        int quantity,
        BigDecimal price
) {
}
