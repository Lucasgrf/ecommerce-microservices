package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.application.dto.UpdateProductInputDTO;
import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.exception.ProductNotFoundException;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Money;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProductUseCase updateProductUseCase;

    private UpdateProductInputDTO input;
    private Product product;

    @BeforeEach
    void setUp() {
        input = new UpdateProductInputDTO(
                "prod-123",
                "cat-456",
                "New Product Name",
                "New Product Desc",
                new BigDecimal("150.00"),
                20
        );

        product = Product.builder()
                .id(new ProductId("prod-123"))
                .categoryId(new CategoryId("old-cat"))
                .name("Old Product Name")
                .description("Old Product Desc")
                .price(new Money(new BigDecimal("100.00")))
                .stock(10)
                .build();
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        // Arrange
        when(productRepository.findById(any(ProductId.class))).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        ProductOutputDTO result = updateProductUseCase.execute(input);

        // Assert
        assertEquals("New Product Name", result.name());
        assertEquals(new BigDecimal("150.00"), result.price());
        assertEquals(20, result.stock());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Arrange
        when(productRepository.findById(any(ProductId.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> updateProductUseCase.execute(input));
        verify(productRepository, never()).save(any(Product.class));
    }
}
