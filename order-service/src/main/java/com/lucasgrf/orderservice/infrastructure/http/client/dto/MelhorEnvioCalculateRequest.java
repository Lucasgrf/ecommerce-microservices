package com.lucasgrf.orderservice.infrastructure.http.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record MelhorEnvioCalculateRequest(
    @JsonProperty("from") PostalCode from,
    @JsonProperty("to") PostalCode to,
    @JsonProperty("products") List<MelhorEnvioProduct> products
) {
    public record PostalCode(@JsonProperty("postal_code") String postalCode) {}

    public record MelhorEnvioProduct(
        String id,
        int width,
        int height,
        int length,
        double weight,
        @JsonProperty("insurance_value") double insuranceValue,
        int quantity
    ) {}
}
