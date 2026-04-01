package com.lucasgrf.notificationservice.application.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderEvent(
        String orderId,
        String customerId,
        String customerEmail,
        BigDecimal total,
        List<OrderEventItem> items
) {
    public record OrderEventItem(
            String productId,
            int quantity,
            BigDecimal price
    ) {}
}
