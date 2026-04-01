package com.lucasgrf.productservice.application.usecase;

import com.lucasgrf.productservice.domain.exception.CategoryNotFoundException;
import com.lucasgrf.productservice.domain.repository.CategoryRepository;
import com.lucasgrf.productservice.domain.valueobject.CategoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void execute(String id) {
        CategoryId categoryId = new CategoryId(id);
        
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        categoryRepository.deleteById(categoryId);
    }
}
