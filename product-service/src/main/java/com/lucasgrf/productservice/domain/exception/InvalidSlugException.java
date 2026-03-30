package com.lucasgrf.productservice.domain.exception;

public class InvalidSlugException extends DomainException {
    public InvalidSlugException() {
        super("Text for slug cannot be null or blank.");
    }
}
