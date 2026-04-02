package com.lucasgrf.orderservice.domain.valueobject;

public record OrderId(String value) {
    public OrderId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("OrderId cannot be empty");
        }
    }
}
