package com.lucasgrf.orderservice.domain.valueobject;

import com.lucasgrf.orderservice.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void shouldCreateAddress() {
        Address addr = new Address("Street 1", "City X", "State Y", "12345-678");
        assertEquals("Street 1", addr.street());
        assertEquals("City X", addr.city());
        assertEquals("State Y", addr.state());
        assertEquals("12345-678", addr.zipCode());
    }

    @Test
    void shouldNotCreateInvalidAddress() {
        assertThrows(DomainException.class, () -> new Address(null, "City X", "State Y", "12345-678"));
        assertThrows(DomainException.class, () -> new Address("Street 1", "", "State Y", "12345-678"));
        assertThrows(DomainException.class, () -> new Address("Street 1", "City X", null, "12345-678"));
        assertThrows(DomainException.class, () -> new Address("Street 1", "City X", "State Y", ""));
    }
}
