package com.lucasgrf.orderservice.domain.valueobject;

import com.lucasgrf.orderservice.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrackingCodeTest {

    @Test
    void shouldCreateTrackingCode() {
        TrackingCode code = new TrackingCode("BR123456789XP");
        assertEquals("BR123456789XP", code.code());
    }

    @Test
    void shouldNotCreateInvalidTrackingCode() {
        assertThrows(DomainException.class, () -> new TrackingCode("BR123XP")); // too short
        assertThrows(DomainException.class, () -> new TrackingCode("BR123456789XPAA")); // too long
    }
}
