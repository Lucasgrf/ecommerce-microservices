package com.lucasgrf.orderservice.application.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderOutputDTO(
        String id,
        String customerId,
        String state,
        BigDecimal total,
        String trackingCode,
        List<OrderItemOutputDTO> items
) {
}
