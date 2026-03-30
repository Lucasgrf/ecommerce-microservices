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
        String paymentUrl,
        List<OrderItemOutputDTO> items
) {
    public static OrderOutputDTO from(Order order) {
        return new OrderOutputDTO(
                order.getId().value(),
                order.getCustomerId(),
                order.getState().getName(),
                order.getTotal().amount(),
                order.getTrackingCode() != null ? order.getTrackingCode().code() : null,
                null, // paymentUrl is only populated at order creation
                order.getItems().stream()
                        .map(i -> new OrderItemOutputDTO(
                                i.getProductId(),
                                i.getQuantity(),
                                i.getPrice().amount(),
                                i.getTotal().amount()
                        )).collect(Collectors.toList())
        );
    }

    /** Used by CreateOrderUseCase to include the payment URL. */
    public static OrderOutputDTO fromWithPaymentUrl(Order order, String paymentUrl) {
        return new OrderOutputDTO(
                order.getId().value(),
                order.getCustomerId(),
                order.getState().getName(),
                order.getTotal().amount(),
                order.getTrackingCode() != null ? order.getTrackingCode().code() : null,
                paymentUrl,
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
