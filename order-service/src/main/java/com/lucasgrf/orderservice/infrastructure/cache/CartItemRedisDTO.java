package com.lucasgrf.orderservice.infrastructure.cache;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lucasgrf.orderservice.domain.entity.CartItem;
import com.lucasgrf.orderservice.domain.valueobject.Money;

import java.math.BigDecimal;

/**
 * Jackson-friendly projection of CartItem for Redis serialization.
 * Kept in the infrastructure layer to keep the domain clean.
 */
public class CartItemRedisDTO {

    public final String productId;
    public final String productName;
    public final int quantity;
    public final BigDecimal unitPrice;

    @JsonCreator
    public CartItemRedisDTO(
            @JsonProperty("productId") String productId,
            @JsonProperty("productName") String productName,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("unitPrice") BigDecimal unitPrice
    ) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public static CartItemRedisDTO from(CartItem item) {
        return new CartItemRedisDTO(
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getUnitPrice().amount()
        );
    }

    public CartItem toDomain() {
        return new CartItem(productId, productName, quantity, new Money(unitPrice));
    }
}
