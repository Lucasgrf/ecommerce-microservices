package com.lucasgrf.productservice.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductVariantDTO(
        @Schema(description = "Size of the product (e.g., S, M, L, XL, 42, 44)", example = "M") String size,

        @Schema(description = "Color of the product", example = "Black") String color,

        @Schema(description = "Stock quantity available for this specific variant", example = "50") Integer stockQuantity,

        @Schema(description = "SKU specific to this variant", example = "TSHIRT-BLK-M") String sku) {
}
