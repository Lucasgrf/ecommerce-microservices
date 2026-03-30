package com.lucasgrf.productservice.domain.valueobject;

import com.lucasgrf.productservice.domain.exception.InvalidSlugException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlugTest {

    @Test
    void shouldCreateSlugFromValidText() {
        Slug slug = Slug.fromText("Tênis Nike Air Max");
        assertEquals("tenis-nike-air-max", slug.value());
    }

    @Test
    void shouldCreateSlugRemovingSpecialChars() {
        Slug slug = Slug.fromText("Camiseta 100% Algodão! (Cores: Azul & Rosa)");
        assertEquals("camiseta-100-algodao-cores-azul-rosa", slug.value());
    }

    @Test
    void shouldThrowExceptionIfTextIsBlank() {
        assertThrows(InvalidSlugException.class, () -> Slug.fromText("  "));
    }
}
