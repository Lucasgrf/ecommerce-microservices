package com.lucasgrf.orderservice.application.dto;

import java.math.BigDecimal;

public record ShippingQuoteDTO(
    String serviceId,
    String serviceName,
    BigDecimal price,
    int deliveryDays,
    String companyName
) {}
