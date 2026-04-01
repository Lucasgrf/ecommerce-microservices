package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.CategoryOutputDTO;
import com.lucasgrf.productservice.domain.entity.Category;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Slug;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCategoriesUseCaseTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ListCategoriesUseCase listCategoriesUseCase;

    @Test
    void shouldReturnListOfCategories() {
        // Arrange
        Category category = Category.builder()
                .id(new CategoryId("123e4567-e89b-12d3-a456-426614174000"))
                .name("Test Category")
                .slug(Slug.fromText("Test Category"))
                .description("Test Description")
                .build();
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        // Act
        List<CategoryOutputDTO> result = listCategoriesUseCase.execute();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Test Category", result.get(0).name());
        assertEquals("test-category", result.get(0).slug());
    }
}
