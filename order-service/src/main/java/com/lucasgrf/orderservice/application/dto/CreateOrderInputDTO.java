package com.lucasgrf.orderservice.application.dto;

import java.util.List;

public record CreateOrderInputDTO(
                String customerId,
                String customerEmail,
                String street,
                String city,
                String state,
                String zipCode,
                List<OrderItemInputDTO> items) {
}
