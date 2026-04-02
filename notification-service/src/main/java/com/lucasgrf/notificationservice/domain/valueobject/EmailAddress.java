package com.lucasgrf.notificationservice.domain.valueobject;

import com.lucasgrf.notificationservice.domain.exception.DomainException;

import java.util.regex.Pattern;

public record EmailAddress(String value) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public EmailAddress {
        if (value == null || value.isBlank()) {
            throw new DomainException("Email address cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new DomainException("Invalid email format: " + value);
        }
    }
}
