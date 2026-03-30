package com.lucasgrf.orderservice.infrastructure.http.adapter;

import com.lucasgrf.orderservice.application.dto.ShippingQuoteDTO;
import com.lucasgrf.orderservice.application.port.ShippingServicePort;
import com.lucasgrf.orderservice.infrastructure.http.client.MelhorEnvioClient;
import com.lucasgrf.orderservice.infrastructure.http.client.dto.MelhorEnvioCalculateRequest;
import com.lucasgrf.orderservice.infrastructure.http.client.dto.MelhorEnvioCalculateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MelhorEnvioAdapter implements ShippingServicePort {

    private final MelhorEnvioClient melhorEnvioClient;

    @Value("${melhor-envio.token}")
    private String token;

    @Value("${melhor-envio.from-zip-code}")
    private String fromZipCode;

    @Override
    public Optional<ShippingQuoteDTO> calculateShipping(String toZipCode, List<ShippingItemInput> items) {
        MelhorEnvioCalculateRequest request = new MelhorEnvioCalculateRequest(
                new MelhorEnvioCalculateRequest.PostalCode(fromZipCode),
                new MelhorEnvioCalculateRequest.PostalCode(toZipCode),
                items.stream().map(item -> new MelhorEnvioCalculateRequest.MelhorEnvioProduct(
                        item.id(),
                        (int) item.width(),
                        (int) item.height(),
                        (int) item.length(),
                        item.weight(),
                        0.0, // insurance_value
                        item.quantity()
                )).collect(Collectors.toList())
        );

        try {
            List<MelhorEnvioCalculateResponse> responses = melhorEnvioClient.calculateShipping("Bearer " + token, request);

            return responses.stream()
                    .filter(res -> res.error() == null)
                    .min(Comparator.comparing(MelhorEnvioCalculateResponse::price))
                    .map(res -> new ShippingQuoteDTO(
                            String.valueOf(res.id()),
                            res.name(),
                            res.price(),
                            res.deliveryTime(),
                            res.company().name()
                    ));
        } catch (Exception e) {
            // In a real scenario, log the error and maybe return empty or a default value
            return Optional.empty();
        }
    }
}
