package com.lucasgrf.orderservice.infrastructure.http.adapter;

import com.lucasgrf.orderservice.application.dto.ProductResponseDTO;
import com.lucasgrf.orderservice.application.port.ProductServicePort;
import com.lucasgrf.orderservice.infrastructure.http.client.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceAdapter implements ProductServicePort {

    private final ProductServiceClient productServiceClient;

    @Override
    public Optional<ProductResponseDTO> getProductById(String id) {
        try {
            return productServiceClient.getProductById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
