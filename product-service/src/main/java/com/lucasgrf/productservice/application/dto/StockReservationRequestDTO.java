package com.lucasgrf.productservice.application.dto;

import java.util.Map;

/**
 * Represents a request to reserve or release stock for a set of products.
 * The map key is the productId (String), and the value is the quantity.
 */
public record StockReservationRequestDTO(
        Map<String, Integer> items
) {
}
