package com.lucasgrf.orderservice.application.dto;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderInputDTO(
        String customerId,
        String street,
        String city,
        String state,
        String zipCode,
        List<OrderItemInputDTO> items
) {
}
