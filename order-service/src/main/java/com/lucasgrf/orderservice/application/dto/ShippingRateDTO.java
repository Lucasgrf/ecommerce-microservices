package com.lucasgrf.orderservice.application.dto;

import java.math.BigDecimal;

public record ShippingRateDTO(
        String serviceName,
        BigDecimal price,
        int deliveryDays,
        String carrier
) {
}
