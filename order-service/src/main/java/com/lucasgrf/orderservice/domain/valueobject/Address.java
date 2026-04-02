package com.lucasgrf.orderservice.domain.valueobject;

import com.lucasgrf.orderservice.domain.exception.DomainException;

public record Address(String street, String city, String state, String zipCode) {
    public Address {
        if (street == null || street.isBlank()) throw new DomainException("Street cannot be empty");
        if (city == null || city.isBlank()) throw new DomainException("City cannot be empty");
        if (state == null || state.isBlank()) throw new DomainException("State cannot be empty");
        if (zipCode == null || zipCode.isBlank()) throw new DomainException("ZipCode cannot be empty");
    }
}
