package com.lucasgrf.productservice.presentation.controller;

import com.lucasgrf.productservice.application.dto.CreateProductInputDTO;
import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.application.dto.ProductSearchCriteria;
import com.lucasgrf.productservice.application.dto.UpdateProductInputDTO;
import com.lucasgrf.productservice.application.usecase.*;
import com.lucasgrf.productservice.presentation.dto.PageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product catalog management endpoints")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final SearchProductsUseCase searchProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<ProductOutputDTO> createProduct(@RequestBody CreateProductInputDTO input) {
        ProductOutputDTO output = createProductUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID")
    public ResponseEntity<ProductOutputDTO> getProductById(@PathVariable String id) {
        ProductOutputDTO output = getProductByIdUseCase.execute(id);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by criteria")
    public ResponseEntity<PageResponseDTO<ProductOutputDTO>> search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ProductSearchCriteria criteria = new ProductSearchCriteria(
                query, categoryId, minPrice, maxPrice, page, size
        );
        PageResponseDTO<ProductOutputDTO> response = searchProductsUseCase.execute(criteria);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    public ResponseEntity<ProductOutputDTO> updateProduct(
            @PathVariable String id,
            @RequestBody UpdateProductInputDTO input
    ) {
        UpdateProductInputDTO inputWithId = new UpdateProductInputDTO(
                id, input.categoryId(), input.name(), input.description(), input.price(), input.stockQuantity()
        );
        return ResponseEntity.ok(updateProductUseCase.execute(inputWithId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by ID")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
