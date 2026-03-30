package com.lucasgrf.productservice.domain.exception;

public class InvalidMoneyException extends DomainException {
    public InvalidMoneyException() {
        super("Invalid money amount. It cannot be null or negative.");
    }
}
