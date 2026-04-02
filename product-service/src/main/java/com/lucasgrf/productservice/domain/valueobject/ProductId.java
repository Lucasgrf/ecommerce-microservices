package com.lucasgrf.productservice.domain.valueobject;

import java.util.UUID;

public record ProductId(String value) {
    public static ProductId generate() {
        return new ProductId(UUID.randomUUID().toString());
    }
}
