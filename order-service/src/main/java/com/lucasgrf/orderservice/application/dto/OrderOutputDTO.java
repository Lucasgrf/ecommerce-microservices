package com.lucasgrf.orderservice.application.dto;

import com.lucasgrf.orderservice.domain.entity.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record OrderOutputDTO(
        String id,
        String customerId,
        String state,
        BigDecimal total,
        String trackingCode,
        List<OrderItemOutputDTO> items
) {
    public static OrderOutputDTO from(Order order) {
        return new OrderOutputDTO(
                order.getId().value(),
                order.getCustomerId(),
                order.getState().getName(),
                order.getTotal().amount(),
                order.getTrackingCode() != null ? order.getTrackingCode().code() : null,
                order.getItems().stream()
                        .map(i -> new OrderItemOutputDTO(
                                i.getProductId(),
                                i.getQuantity(),
                                i.getPrice().amount(),
                                i.getTotal().amount()
                        )).collect(Collectors.toList())
        );
    }
}
