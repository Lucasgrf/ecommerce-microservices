package com.lucasgrf.orderservice.application.port;

import com.lucasgrf.orderservice.application.dto.ProductResponseDTO;
import java.util.Optional;

public interface ProductServicePort {
    Optional<ProductResponseDTO> getProductById(String id);
}
