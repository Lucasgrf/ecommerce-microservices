package com.lucasgrf.orderservice.application.dto;

public record UpdateOrderStatusInputDTO(
        String orderId,
        OrderCommand command,
        String trackingCode,
        String reason
) {
    public enum OrderCommand {
        PAY, SHIP, DELIVER, CANCEL
    }
}
