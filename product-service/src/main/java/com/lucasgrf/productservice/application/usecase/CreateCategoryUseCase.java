package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.CategoryOutputDTO;
import com.lucasgrf.productservice.application.dto.CreateCategoryInputDTO;
import com.lucasgrf.productservice.domain.entity.Category;
import com.lucasgrf.productservice.domain.exception.DuplicateCategoryException;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import com.lucasgrf.productservice.domain.valueobject.Slug;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public CategoryOutputDTO execute(CreateCategoryInputDTO input) {
        Slug slug = Slug.fromText(input.name());

        if (categoryRepository.existsBySlug(slug)) {
            throw new DuplicateCategoryException(slug.value());
        }

        Category category = Category.builder()
                .id(CategoryId.generate())
                .name(input.name())
                .description(input.description())
                .slug(slug)
                .build();

        Category saved = categoryRepository.save(category);

        return CategoryOutputDTO.from(saved);
    }
}
