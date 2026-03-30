package com.lucasgrf.productservice.domain.exception;

public class DuplicateCategoryException extends DomainException {
    public DuplicateCategoryException(String slug) {
        super("Category with slug '" + slug + "' already exists.");
    }
}
