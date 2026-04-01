package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.domain.exception.CategoryNotFoundException;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
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
class DeleteCategoryUseCaseTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @Test
    void shouldDeleteCategorySuccessfully() {
        // Arrange
        String categoryId = "123e4567-e89b-12d3-a456-426614174000";
        when(categoryRepository.findById(any(CategoryId.class))).thenReturn(Optional.of(com.lucasgrf.productservice.domain.entity.Category.builder().build()));

        // Act
        deleteCategoryUseCase.execute(categoryId);

        // Assert
        verify(categoryRepository, times(1)).deleteById(any(CategoryId.class));
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        // Arrange
        String categoryId = "123e4567-e89b-12d3-a456-426614174000";
        when(categoryRepository.findById(any(CategoryId.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> deleteCategoryUseCase.execute(categoryId));
        verify(categoryRepository, never()).deleteById(any(CategoryId.class));
    }
}
