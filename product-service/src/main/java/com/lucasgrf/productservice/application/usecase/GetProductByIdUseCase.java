package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.domain.exception.ProductNotFoundException;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetProductByIdUseCase {

    private final ProductRepository productRepository;

    public ProductOutputDTO execute(String id) {
        return productRepository.findById(new ProductId(id))
                .map(product -> new ProductOutputDTO(
                        product.getId().value(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice().amount(),
                        product.getCategoryId() != null ? product.getCategoryId().value() : null,
                        product.getStock(),
                        product.getImages(),
                        List.of(), // Variants skip for now
                        product.isActive()
                ))
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
