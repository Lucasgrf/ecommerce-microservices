package com.lucasgrf.productservice.presentation.controller;

import com.lucasgrf.productservice.application.dto.CategoryOutputDTO;
import com.lucasgrf.productservice.application.dto.CreateCategoryInputDTO;
import com.lucasgrf.productservice.application.usecase.CreateCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;

    @PostMapping
    public ResponseEntity<CategoryOutputDTO> createCategory(@RequestBody CreateCategoryInputDTO input) {
        CategoryOutputDTO output = createCategoryUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }
}
