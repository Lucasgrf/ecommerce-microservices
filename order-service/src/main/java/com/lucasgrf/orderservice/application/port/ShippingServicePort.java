package com.lucasgrf.orderservice.application.port;

import com.lucasgrf.orderservice.application.dto.ShippingQuoteDTO;
import java.util.List;
import java.util.Optional;

public interface ShippingServicePort {
    Optional<ShippingQuoteDTO> calculateShipping(String toZipCode, List<ShippingItemInput> items);

    record ShippingItemInput(String id, int quantity, double width, double height, double length, double weight) {}
}
