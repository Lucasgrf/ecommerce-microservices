package com.lucasgrf.productservice.domain.valueobject;

import com.lucasgrf.productservice.domain.exception.InvalidMoneyException;

import java.math.BigDecimal;

public record Money(BigDecimal amount) {
    public Money {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMoneyException();
        }
    }
}
