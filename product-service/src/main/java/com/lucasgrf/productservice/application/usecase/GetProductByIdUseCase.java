package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.domain.exception.ProductNotFoundException;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProductByIdUseCase {

    private final ProductRepository productRepository;

    public ProductOutputDTO execute(String id) {
        return productRepository.findById(new ProductId(id))
                .map(ProductOutputDTO::from)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
