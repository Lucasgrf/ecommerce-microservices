package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.CategoryOutputDTO;
import com.lucasgrf.productservice.application.dto.CreateCategoryInputDTO;
import com.lucasgrf.productservice.domain.entity.Category;
import com.lucasgrf.productservice.domain.exception.DuplicateCategoryException;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import com.lucasgrf.productservice.domain.valueobject.Slug;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCategoryUseCaseTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CreateCategoryUseCase createCategoryUseCase;

    @Test
    void shouldCreateCategorySuccessfully() {
        CreateCategoryInputDTO input = new CreateCategoryInputDTO("Roupas", "Vestuário em geral");

        when(categoryRepository.existsBySlug(any(Slug.class))).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CategoryOutputDTO output = createCategoryUseCase.execute(input);

        assertNotNull(output);
        assertEquals("Roupas", output.name());
        assertEquals("roupas", output.slug());
        assertEquals("Vestuário em geral", output.description());

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void shouldThrowExceptionWhenSlugAlreadyExists() {
        CreateCategoryInputDTO input = new CreateCategoryInputDTO("Roupas", "Vestuário em geral");

        when(categoryRepository.existsBySlug(any(Slug.class))).thenReturn(true);

        assertThrows(DuplicateCategoryException.class, () -> createCategoryUseCase.execute(input));

        verify(categoryRepository, never()).save(any(Category.class));
    }
}
