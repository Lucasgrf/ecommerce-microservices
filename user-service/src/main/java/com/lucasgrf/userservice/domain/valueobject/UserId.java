package com.lucasgrf.userservice.domain.valueobject;

import java.util.UUID;

public record UserId(String value) {
    public UserId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        }
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID().toString());
    }
}
