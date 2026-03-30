package com.lucasgrf.orderservice.infrastructure.http.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record MelhorEnvioCalculateResponse(
    int id,
    String name,
    BigDecimal price,
    @JsonProperty("delivery_time") int deliveryTime,
    Company company,
    String error
) {
    public record Company(int id, String name, String picture) {}
}
