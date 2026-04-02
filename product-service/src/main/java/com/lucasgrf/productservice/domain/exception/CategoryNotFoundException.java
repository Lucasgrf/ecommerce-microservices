package com.lucasgrf.productservice.domain.exception;

public class CategoryNotFoundException extends DomainException {
    public CategoryNotFoundException(String id) {
        super("Category with id '" + id + "' not found.");
    }
}
