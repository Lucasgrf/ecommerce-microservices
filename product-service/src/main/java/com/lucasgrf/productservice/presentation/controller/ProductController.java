package com.lucasgrf.productservice.presentation.controller;

import com.lucasgrf.productservice.application.dto.CreateProductInputDTO;
import com.lucasgrf.productservice.application.dto.ProductOutputDTO;
import com.lucasgrf.productservice.application.dto.ProductSearchCriteria;
import com.lucasgrf.productservice.application.usecase.CreateProductUseCase;
import com.lucasgrf.productservice.application.usecase.SearchProductsUseCase;
import com.lucasgrf.productservice.presentation.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final SearchProductsUseCase searchProductsUseCase;

    @PostMapping
    public ResponseEntity<ProductOutputDTO> createProduct(@RequestBody CreateProductInputDTO input) {
        ProductOutputDTO output = createProductUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping("/search")
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
}
