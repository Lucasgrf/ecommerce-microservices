package com.lucasgrf.orderservice.application.dto;

import java.math.BigDecimal;

public record CartItemOutputDTO(
        String productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {}
