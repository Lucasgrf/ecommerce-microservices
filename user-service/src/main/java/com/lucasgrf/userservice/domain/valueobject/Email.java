package com.lucasgrf.userservice.domain.valueobject;

import com.lucasgrf.userservice.domain.exception.DomainException;

public record Email(String value) {
    public Email {
        if (value == null || !value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new DomainException("Invalid email format: " + value);
        }
    }
}
