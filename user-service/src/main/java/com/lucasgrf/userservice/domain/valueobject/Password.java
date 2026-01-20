package com.lucasgrf.userservice.domain.valueobject;

import com.lucasgrf.userservice.domain.exception.DomainException;

public record Password(String hashedValue) {
    public Password {
        if (hashedValue == null || hashedValue.isBlank()) {
            throw new DomainException("Password cannot be empty");
        }
    }
}
