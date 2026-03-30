package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.CreateProductInputDTO;
import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.exception.CategoryNotFoundException;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Money;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductOutputDTO execute(CreateProductInputDTO input) {
        CategoryId categoryId = null;
        if (input.categoryId() != null) {
            categoryId = new CategoryId(input.categoryId());
            if (categoryRepository.findById(categoryId).isEmpty()) {
                throw new CategoryNotFoundException(input.categoryId());
            }
        }

        Product product = Product.builder()
                .id(ProductId.generate())
                .categoryId(categoryId)
                .name(input.name())
                .description(input.description())
                .price(new Money(input.price()))
                .stock(input.stock())
                .images(input.images())
                .variants(input.variants())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        Product saved = productRepository.save(product);

        return ProductOutputDTO.from(saved);
    }
}
