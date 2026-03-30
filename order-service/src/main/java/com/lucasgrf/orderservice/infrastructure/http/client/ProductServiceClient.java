package com.lucasgrf.orderservice.infrastructure.http.client;

import com.lucasgrf.orderservice.application.dto.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "product-service", url = "${product-service.url:http://localhost:8081}")
public interface ProductServiceClient {

    @GetMapping("/products/{id}")
    Optional<ProductResponseDTO> getProductById(@PathVariable("id") String id);
}
