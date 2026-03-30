package com.lucasgrf.orderservice.domain.valueobject;

import com.lucasgrf.orderservice.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoney() {
        Money money = new Money(new BigDecimal("100.00"));
        assertEquals(new BigDecimal("100.00"), money.amount());
    }

    @Test
    void shouldNotCreateMoneyWithNegativeAmount() {
        assertThrows(DomainException.class, () -> new Money(new BigDecimal("-1.00")));
    }

    @Test
    void shouldAddMoney() {
        Money m1 = new Money(new BigDecimal("50.00"));
        Money m2 = new Money(new BigDecimal("25.00"));
        Money sum = m1.add(m2);

        assertEquals(new BigDecimal("75.00"), sum.amount());
    }

    @Test
    void shouldMultiplyMoney() {
        Money m = new Money(new BigDecimal("50.00"));
        Money result = m.multiply(3);

        assertEquals(new BigDecimal("150.00"), result.amount());
    }
}
