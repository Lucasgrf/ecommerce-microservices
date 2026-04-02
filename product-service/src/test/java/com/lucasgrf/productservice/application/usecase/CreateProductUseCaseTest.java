package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.CreateProductInputDTO;
import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.domain.entity.Product;
import com.lucasgrf.productservice.domain.exception.CategoryNotFoundException;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import com.lucasgrf.productservice.domain.repository.ProductRepository;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CreateProductUseCase createProductUseCase;

    @Test
    void shouldCreateProductSuccessfully() {
        String categoryId = CategoryId.generate().value();
        CreateProductInputDTO input = new CreateProductInputDTO(
                "Camiseta", "Preta M", new BigDecimal("50.00"), categoryId, 10, List.of(), List.of("M"),
                0.5, 10.0, 5.0, 15.0
        );

        when(categoryRepository.findById(any(CategoryId.class))).thenReturn(Optional.of(mock(com.lucasgrf.productservice.domain.entity.Category.class)));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductOutputDTO output = createProductUseCase.execute(input);

        assertNotNull(output);
        assertEquals("Camiseta", output.name());
        assertEquals(new BigDecimal("50.00"), output.price());
        assertEquals(categoryId, output.categoryId());
        assertTrue(output.active());
        
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionIfCategoryDoesNotExist() {
        String categoryId = CategoryId.generate().value();
        CreateProductInputDTO input = new CreateProductInputDTO(
                "Camiseta", "Preta M", new BigDecimal("50.00"), categoryId, 10, List.of(), List.of("M"),
                0.5, 10.0, 5.0, 15.0
        );

        when(categoryRepository.findById(any(CategoryId.class))).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> createProductUseCase.execute(input));
        
        verify(productRepository, never()).save(any(Product.class));
    }
}
