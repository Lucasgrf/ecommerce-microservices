package com.lucasgrf.productservice.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record PageResponseDTO<T>(
        @Schema(description = "List of items in the current page") List<T> content,

        @Schema(description = "Current page number (0-indexed)", example = "0") Integer pageNumber,

        @Schema(description = "Size of the page", example = "20") Integer pageSize,

        @Schema(description = "Total number of elements across all pages", example = "100") Long totalElements,

        @Schema(description = "Total number of pages", example = "5") Integer totalPages) {
}
