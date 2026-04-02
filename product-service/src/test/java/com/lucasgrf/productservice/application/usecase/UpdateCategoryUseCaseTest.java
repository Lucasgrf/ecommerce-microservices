package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.CategoryOutputDTO;
import com.lucasgrf.productservice.application.dto.UpdateCategoryInputDTO;
import com.lucasgrf.productservice.domain.entity.Category;
import com.lucasgrf.productservice.domain.exception.CategoryNotFoundException;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Slug;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryUseCaseTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private UpdateCategoryUseCase updateCategoryUseCase;

    private UpdateCategoryInputDTO input;
    private Category category;

    @BeforeEach
    void setUp() {
        input = new UpdateCategoryInputDTO("123e4567-e89b-12d3-a456-426614174000", "New Name", "New Description");
        category = Category.builder()
                .id(new CategoryId("123e4567-e89b-12d3-a456-426614174000"))
                .name("Old Name")
                .slug(Slug.fromText("Old Name"))
                .description("Old Description")
                .build();
    }

    @Test
    void shouldUpdateCategorySuccessfully() {
        // Arrange
        when(categoryRepository.findById(any(CategoryId.class))).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Act
        CategoryOutputDTO result = updateCategoryUseCase.execute(input);

        // Assert
        assertEquals("New Name", result.name());
        assertEquals("new-name", result.slug());
        assertEquals("New Description", result.description());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        // Arrange
        when(categoryRepository.findById(any(CategoryId.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> updateCategoryUseCase.execute(input));
        verify(categoryRepository, never()).save(any(Category.class));
    }
}
