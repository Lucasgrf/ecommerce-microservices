package com.lucasgrf.orderservice.infrastructure.http.adapter;

import com.lucasgrf.orderservice.application.dto.ProductResponseDTO;
import com.lucasgrf.orderservice.application.port.ProductServicePort;
import com.lucasgrf.orderservice.infrastructure.http.client.ProductServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceAdapter implements ProductServicePort {

    private final ProductServiceClient productServiceClient;

    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "getProductByIdFallback")
    @Retry(name = "productService")
    public Optional<ProductResponseDTO> getProductById(String id) {
        return productServiceClient.getProductById(id);
    }

    @SuppressWarnings("unused") // Invoked by Resilience4j via reflection
    private Optional<ProductResponseDTO> getProductByIdFallback(String id, Throwable throwable) {
        log.warn("ProductService circuit open or retry exhausted for product id={}. Cause: {}",
                id, throwable.getMessage());
        return Optional.empty();
    }
}
