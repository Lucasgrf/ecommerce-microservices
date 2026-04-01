package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.application.dto.CategoryOutputDTO;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListCategoriesUseCase {

    private final CategoryRepository categoryRepository;

    public List<CategoryOutputDTO> execute() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryOutputDTO(
                        category.getId().value(),
                        category.getName(),
                        category.getSlug().value(),
                        category.getDescription()
                ))
                .collect(Collectors.toList());
    }
}
