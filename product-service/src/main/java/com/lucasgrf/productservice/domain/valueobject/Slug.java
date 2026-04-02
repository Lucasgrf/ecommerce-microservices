package com.lucasgrf.productservice.domain.valueobject;

import com.lucasgrf.productservice.domain.exception.InvalidSlugException;

import java.text.Normalizer;

public record Slug(String value) {

    public static Slug fromText(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new InvalidSlugException();
        }
        
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // Remove special chars except space and hyphen
                .trim()
                .replaceAll("\\s+", "-") // Replace spaces with hyphens
                .replaceAll("-+", "-"); // Replace multiple hyphens with single one

        if (normalized.isEmpty()) {
             throw new InvalidSlugException();
        }

        return new Slug(normalized);
    }
}
