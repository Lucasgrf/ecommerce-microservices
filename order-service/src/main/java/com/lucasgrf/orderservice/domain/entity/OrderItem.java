package com.lucasgrf.orderservice.domain.entity;

import com.lucasgrf.orderservice.domain.exception.DomainException;
import com.lucasgrf.orderservice.domain.valueobject.Money;
import lombok.Getter;

@Getter
public class OrderItem {

    private final String productId;
    private final int quantity;
    private final Money price;

    public OrderItem(String productId, int quantity, Money price) {
        if (productId == null || productId.isBlank()) throw new DomainException("Product ID cannot be empty");
        if (quantity <= 0) throw new DomainException("Quantity must be greater than zero");
        if (price == null) throw new DomainException("Price cannot be null");

        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Money getTotal() {
        return price.multiply(quantity);
    }
}
