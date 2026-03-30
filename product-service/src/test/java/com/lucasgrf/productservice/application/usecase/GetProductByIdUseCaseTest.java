package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.exception.ProductNotFoundException;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.Money;
import com.lucasgrf.productservice.domain.valueobject.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetProductByIdUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    private GetProductByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new GetProductByIdUseCase(productRepository);
    }

    @Test
    void shouldReturnProductOutputDTOWhenProductExists() {
        String idStr = "prod_123";
        ProductId productId = new ProductId(idStr);
        Product product = Product.builder()
                .id(productId)
                .name("Test Product")
                .description("Desc")
                .price(new Money(new BigDecimal("99.99")))
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductOutputDTO outputDTO = useCase.execute(idStr);

        assertNotNull(outputDTO);
        assertEquals(idStr, outputDTO.id());
        assertEquals("Test Product", outputDTO.name());
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenProductDoesNotExist() {
        String idStr = "non_existent";
        ProductId productId = new ProductId(idStr);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> useCase.execute(idStr));
    }
}
