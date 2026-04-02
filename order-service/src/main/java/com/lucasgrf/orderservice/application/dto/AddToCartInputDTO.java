package com.lucasgrf.orderservice.application.dto;

import java.math.BigDecimal;

public record AddToCartInputDTO(
        String customerId,
        String productId,
        String productName,
        int quantity,
        BigDecimal unitPrice
) {}
