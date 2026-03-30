package com.lucasgrf.orderservice.domain.valueobject;

import com.lucasgrf.orderservice.domain.exception.DomainException;

public record TrackingCode(String code) {
    public TrackingCode {
        if (code == null || code.length() != 13) {
            throw new DomainException("Tracking Code must have exactly 13 characters");
        }
    }
}
