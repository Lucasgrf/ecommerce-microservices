package com.lucasgrf.productservice.domain.valueobject;

import com.lucasgrf.productservice.domain.exception.InvalidMoneyException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoneyWhenValueIsPositive() {
        Money money = new Money(new BigDecimal("10.50"));
        assertEquals(new BigDecimal("10.50"), money.amount());
    }

    @Test
    void shouldCreateMoneyWhenValueIsZero() {
        Money money = new Money(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, money.amount());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNegative() {
        assertThrows(InvalidMoneyException.class, () -> new Money(new BigDecimal("-5.00")));
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        assertThrows(InvalidMoneyException.class, () -> new Money(null));
    }
}
