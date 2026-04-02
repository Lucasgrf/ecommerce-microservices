package com.lucasgrf.orderservice.domain.entity;

import com.lucasgrf.orderservice.domain.exception.DomainException;
import com.lucasgrf.orderservice.domain.valueobject.Money;
import lombok.Getter;

@Getter
public class CartItem {

    private final String productId;
    private final String productName;
    private int quantity;
    private final Money unitPrice;

    public CartItem(String productId, String productName, int quantity, Money unitPrice) {
        if (productId == null || productId.isBlank()) throw new DomainException("Cart item productId cannot be empty");
        if (productName == null || productName.isBlank()) throw new DomainException("Cart item productName cannot be empty");
        if (quantity <= 0) throw new DomainException("Cart item quantity must be greater than zero");
        if (unitPrice == null) throw new DomainException("Cart item unitPrice cannot be null");

        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) throw new DomainException("Quantity increment must be positive");
        this.quantity += amount;
    }

    public Money getSubtotal() {
        return unitPrice.multiply(quantity);
    }
}
