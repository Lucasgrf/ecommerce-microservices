package com.lucasgrf.productservice.presentation.controller;

import com.lucasgrf.productservice.application.dto.StockReservationRequestDTO;
import com.lucasgrf.productservice.application.usecase.ReleaseStockUseCase;
import com.lucasgrf.productservice.application.usecase.ReserveStockUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Internal API consumed exclusively by order-service for stock management.
 * This endpoint is NOT exposed to the public internet — it should be
 * protected at the API Gateway level or via network policies.
 */
@RestController
@RequestMapping("/internal/stock")
@RequiredArgsConstructor
@Tag(name = "Internal Stock API", description = "Internal endpoint for stock reservation/release")
public class StockController {

    private final ReserveStockUseCase reserveStockUseCase;
    private final ReleaseStockUseCase releaseStockUseCase;

    /**
     * Atomically reserves stock for a set of products.
     * Called by order-service when creating a new order.
     */
    @PostMapping("/reserve")
    @Operation(summary = "Reserve stock for an order (internal use only)")
    public ResponseEntity<Void> reserve(@RequestBody StockReservationRequestDTO request) {
        reserveStockUseCase.execute(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Releases previously reserved stock.
     * Called by order-service on order cancellation or payment failure.
     */
    @PostMapping("/release")
    @Operation(summary = "Release reserved stock (internal use only)")
    public ResponseEntity<Void> release(@RequestBody StockReservationRequestDTO request) {
        releaseStockUseCase.execute(request);
        return ResponseEntity.ok().build();
    }
}
