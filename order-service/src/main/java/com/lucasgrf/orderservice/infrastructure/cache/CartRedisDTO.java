package com.lucasgrf.orderservice.infrastructure.cache;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucasgrf.orderservice.domain.entity.Cart;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON-serializable snapshot of a Cart for Redis storage.
 * Pattern: infrastructure serialization DTO isolates Jackson from domain.
 */
public class CartRedisDTO {

    public final String customerId;
    public final List<CartItemRedisDTO> items;

    @JsonCreator
    public CartRedisDTO(
            @JsonProperty("customerId") String customerId,
            @JsonProperty("items") List<CartItemRedisDTO> items
    ) {
        this.customerId = customerId;
        this.items = items;
    }

    public static CartRedisDTO from(Cart cart) {
        List<CartItemRedisDTO> items = cart.getItems().stream()
                .map(CartItemRedisDTO::from)
                .collect(Collectors.toList());
        return new CartRedisDTO(cart.getCustomerId(), items);
    }

    public Cart toDomain() {
        List<com.lucasgrf.orderservice.domain.entity.CartItem> domainItems = items.stream()
                .map(CartItemRedisDTO::toDomain)
                .collect(Collectors.toList());
        return new Cart(customerId, domainItems);
    }
}
