package com.lucasgrf.productservice.domain.valueobject;

import java.util.UUID;

public record CategoryId(String value) {
    public static CategoryId generate() {
        return new CategoryId(UUID.randomUUID().toString());
    }
}
