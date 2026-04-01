package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.CategoryOutputDTO;
import com.lucasgrf.productservice.application.dto.UpdateCategoryInputDTO;
import com.lucasgrf.productservice.domain.entity.Category;
import com.lucasgrf.productservice.domain.exception.CategoryNotFoundException;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    public CategoryOutputDTO execute(UpdateCategoryInputDTO input) {
        CategoryId id = new CategoryId(input.id());
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(input.id()));

        category.updateName(input.name());
        category.updateDescription(input.description());

        Category savedCategory = categoryRepository.save(category);

        return new CategoryOutputDTO(
                savedCategory.getId().value(),
                savedCategory.getName(),
                savedCategory.getSlug().value(),
                savedCategory.getDescription()
        );
    }
}
