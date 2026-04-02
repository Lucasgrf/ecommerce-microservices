package com.lucasgrf.orderservice.domain.valueobject;

import com.lucasgrf.orderservice.domain.exception.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) {

    public Money {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("Money amount cannot be negative or null");
        }
        amount = amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public Money add(Money money) {
        return new Money(this.amount.add(money.amount()));
    }

    public Money multiply(int multiplier) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)));
    }
}
