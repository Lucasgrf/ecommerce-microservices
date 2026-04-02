package com.lucasgrf.productservice.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record ProductResponseDTO(
        @Schema(description = "Unique identifier of the product", example = "65abc123def4567890") String id,

        @Schema(description = "Product Name", example = "Basic Cotton T-Shirt") String name,

        @Schema(description = "Detailed product description", example = "100% Cotton, comfortable fit.") String description,

        @Schema(description = "Price of the product", example = "49.90") BigDecimal price,

        @Schema(description = "Category of the product", example = "T-Shirts") String category,

        @Schema(description = "List of image URLs for the product") List<String> images,

        @Schema(description = "Dynamic attributes (e.g., Fabric, Voltage)", example = "{\"fabric\": \"Cotton\"}") Map<String, String> attributes,

        @Schema(description = "Available product variations (Size, Color, Stock, etc.)") List<ProductVariantDTO> variants) {
}
