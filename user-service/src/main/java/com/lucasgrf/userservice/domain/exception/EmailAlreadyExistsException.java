package com.lucasgrf.userservice.domain.exception;

public class EmailAlreadyExistsException extends DomainException {
    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}
