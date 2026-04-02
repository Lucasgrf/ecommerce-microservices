package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.domain.exception.ProductNotFoundException;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductUseCase deleteProductUseCase;

    @Test
    void shouldDeleteProductSuccessfully() {
        // Arrange
        String productId = "prod-123";
        when(productRepository.findById(any(ProductId.class))).thenReturn(Optional.of(com.lucasgrf.productservice.domain.entity.Product.builder().build()));

        // Act
        deleteProductUseCase.execute(productId);

        // Assert
        verify(productRepository, times(1)).delete(any(ProductId.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Arrange
        String productId = "prod-123";
        when(productRepository.findById(any(ProductId.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> deleteProductUseCase.execute(productId));
        verify(productRepository, never()).delete(any(ProductId.class));
    }
}
