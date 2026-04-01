package com.lucasgrf.orderservice.application.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartOutputDTO(
        String customerId,
        List<CartItemOutputDTO> items,
        BigDecimal total
) {}
