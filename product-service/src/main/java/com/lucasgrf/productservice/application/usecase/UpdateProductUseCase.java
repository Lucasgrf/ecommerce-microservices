package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.application.dto.UpdateProductInputDTO;
import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.exception.ProductNotFoundException;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Money;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCase {

    private final ProductRepository productRepository;

    public ProductOutputDTO execute(UpdateProductInputDTO input) {
        ProductId id = new ProductId(input.id());
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(input.id()));

        CategoryId newCategoryId = input.categoryId() != null ? new CategoryId(input.categoryId()) : null;
        Money newPrice = input.price() != null ? new Money(input.price()) : null;

        product.updateDetails(
                input.name(),
                input.description(),
                newPrice,
                newCategoryId,
                null, // keeping images unchanged for this MVP DTO
                null  // keeping variants unchanged for this MVP DTO
        );

        if (input.stockQuantity() != null) {
            product.updateStock(input.stockQuantity());
        }

        Product savedProduct = productRepository.save(product);

        return ProductOutputDTO.from(savedProduct);
    }
}
