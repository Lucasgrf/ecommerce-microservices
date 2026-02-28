package com.lucasgrf.productservice.presentation.controller;

import com.lucasgrf.productservice.presentation.dto.PageResponseDTO;
import com.lucasgrf.productservice.presentation.dto.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product Catalog", description = "Endpoints for searching and viewing products")
public class ProductController {

    @Operation(summary = "Search products", description = "Get a paginated list of products with optional filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or filter parameters")
    })
    @GetMapping
    public ResponseEntity<PageResponseDTO<ProductResponseDTO>> searchProducts(
            @Parameter(description = "Search query term", example = "shirt") @RequestParam(required = false) String query,

            @Parameter(description = "Filter by category", example = "Clothing") @RequestParam(required = false) String category,

            @Parameter(description = "Minimum price filter", example = "10.00") @RequestParam(required = false) BigDecimal minPrice,

            @Parameter(description = "Maximum price filter", example = "100.00") @RequestParam(required = false) BigDecimal maxPrice,

            @Parameter(description = "Page number (0-indexed)", example = "0") @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Page size", example = "20") @RequestParam(defaultValue = "20") int size) {
        // SDD ONLY - Interface implemented without concrete logic for the Swagger
        // design phase
        return ResponseEntity.ok(new PageResponseDTO<>(
                List.of(), page, size, 0L, 0));
    }

    @Operation(summary = "Get product details", description = "Retrieve complete details, images, and variants of a specific product.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product successfully found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductDetails(
            @Parameter(description = "Product ID (MongoDB ObjectId format)", example = "65abc123def4567890") @PathVariable String id) {
        // SDD ONLY - Interface implemented without concrete logic for the Swagger
        // design phase
        return ResponseEntity.ok(null);
    }
}
