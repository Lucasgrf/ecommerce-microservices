package com.lucasgrf.orderservice.domain.entity;

import com.lucasgrf.orderservice.domain.valueobject.Money;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Aggregate root representing a customer's shopping cart.
 * A cart has a 1:1 relationship with a customer (customerId is the cart key).
 * It lives exclusively in Redis and is independent from the Order aggregate.
 */
@Getter
public class Cart {

    private final String customerId;
    private final List<CartItem> items;

    public Cart(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            throw new com.lucasgrf.orderservice.domain.exception.DomainException("Cart customerId cannot be empty");
        }
        this.customerId = customerId;
        this.items = new ArrayList<>();
    }

    // For deserialization (Redis)
    public Cart(String customerId, List<CartItem> items) {
        this.customerId = customerId;
        this.items = new ArrayList<>(items);
    }

    /**
     * Add an item. If the product already exists in the cart, increases its quantity
     * instead of duplicating the entry (merge semantics).
     */
    public void addItem(CartItem newItem) {
        Optional<CartItem> existing = items.stream()
                .filter(i -> i.getProductId().equals(newItem.getProductId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().increaseQuantity(newItem.getQuantity());
        } else {
            items.add(newItem);
        }
    }

    /**
     * Remove an item by productId. No-op if the product is not in the cart.
     */
    public void removeItem(String productId) {
        items.removeIf(i -> i.getProductId().equals(productId));
    }

    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Money getTotal() {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(Money.zero(), Money::add);
    }
}
