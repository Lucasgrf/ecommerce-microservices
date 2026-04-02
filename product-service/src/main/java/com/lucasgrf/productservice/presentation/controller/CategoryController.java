package com.lucasgrf.productservice.presentation.controller;

import com.lucasgrf.productservice.application.dto.CategoryOutputDTO;
import com.lucasgrf.productservice.application.dto.CreateCategoryInputDTO;
import com.lucasgrf.productservice.application.dto.UpdateCategoryInputDTO;
import com.lucasgrf.productservice.application.usecase.CreateCategoryUseCase;
import com.lucasgrf.productservice.application.usecase.DeleteCategoryUseCase;
import com.lucasgrf.productservice.application.usecase.ListCategoriesUseCase;
import com.lucasgrf.productservice.application.usecase.UpdateCategoryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Product category management endpoints")
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<CategoryOutputDTO> createCategory(@RequestBody CreateCategoryInputDTO input) {
        CategoryOutputDTO output = createCategoryUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping
    @Operation(summary = "List all categories")
    public ResponseEntity<List<CategoryOutputDTO>> listCategories() {
        return ResponseEntity.ok(listCategoriesUseCase.execute());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category")
    public ResponseEntity<CategoryOutputDTO> updateCategory(
            @PathVariable String id,
            @RequestBody UpdateCategoryInputDTO input
    ) {
        UpdateCategoryInputDTO inputWithId = new UpdateCategoryInputDTO(id, input.name(), input.description());
        return ResponseEntity.ok(updateCategoryUseCase.execute(inputWithId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category by ID")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        deleteCategoryUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
