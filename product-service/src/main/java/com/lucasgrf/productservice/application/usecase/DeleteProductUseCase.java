package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.domain.exception.ProductNotFoundException;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteProductUseCase {

    private final ProductRepository productRepository;

    @Transactional
    public void execute(String id) {
        ProductId productId = new ProductId(id);
        
        productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(productId);
    }
}
