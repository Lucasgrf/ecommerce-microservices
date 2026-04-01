package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.StockReservationRequestDTO;
import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.exception.DomainException;
import com.lucasgrf.productservice.domain.exception.ProductNotFoundException;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.Money;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReserveStockUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ReserveStockUseCase reserveStockUseCase;

    private Product buildProduct(String id, int stock) {
        return Product.builder()
                .id(new ProductId(id))
                .name("Test Product")
                .price(new Money(BigDecimal.TEN))
                .stock(stock)
                .build();
    }

    @Test
    void shouldReserveStockSuccessfully() {
        // Arrange
        String productId = "prod-001";
        Product product = buildProduct(productId, 100);
        when(productRepository.findById(new ProductId(productId))).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);

        StockReservationRequestDTO request = new StockReservationRequestDTO(Map.of(productId, 10));

        // Act
        reserveStockUseCase.execute(request);

        // Assert: stock was decremented and saved
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldThrowWhenInsufficientStock() {
        // Arrange
        String productId = "prod-002";
        Product product = buildProduct(productId, 5); // only 5 in stock
        when(productRepository.findById(new ProductId(productId))).thenReturn(Optional.of(product));

        StockReservationRequestDTO request = new StockReservationRequestDTO(Map.of(productId, 10));

        // Act & Assert
        assertThrows(DomainException.class, () -> reserveStockUseCase.execute(request));
        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        // Arrange
        String productId = "ghost-product";
        when(productRepository.findById(any(ProductId.class))).thenReturn(Optional.empty());

        StockReservationRequestDTO request = new StockReservationRequestDTO(Map.of(productId, 3));

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> reserveStockUseCase.execute(request));
    }
}
