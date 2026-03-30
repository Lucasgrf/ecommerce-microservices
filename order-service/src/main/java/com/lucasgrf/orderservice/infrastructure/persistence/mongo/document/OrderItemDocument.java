package com.lucasgrf.orderservice.infrastructure.persistence.mongo.document;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderItemDocument {
    private String productId;
    private int quantity;
    private BigDecimal price;
}
