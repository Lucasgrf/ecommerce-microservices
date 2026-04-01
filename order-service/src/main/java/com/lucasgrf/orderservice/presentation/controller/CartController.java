package com.lucasgrf.orderservice.presentation.controller;

import com.lucasgrf.orderservice.application.dto.AddToCartInputDTO;
import com.lucasgrf.orderservice.application.dto.CartOutputDTO;
import com.lucasgrf.orderservice.application.usecase.AddToCartUseCase;
import com.lucasgrf.orderservice.application.usecase.ClearCartUseCase;
import com.lucasgrf.orderservice.application.usecase.GetCartUseCase;
import com.lucasgrf.orderservice.application.usecase.RemoveFromCartUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart", description = "Redis-backed shopping cart endpoints")
public class CartController {

    private final AddToCartUseCase addToCartUseCase;
    private final GetCartUseCase getCartUseCase;
    private final RemoveFromCartUseCase removeFromCartUseCase;
    private final ClearCartUseCase clearCartUseCase;

    @PostMapping("/items")
    @Operation(summary = "Add an item to the cart (merges quantity if product already exists)")
    public ResponseEntity<CartOutputDTO> addItem(@RequestBody AddToCartInputDTO input) {
        return ResponseEntity.ok(addToCartUseCase.execute(input));
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get the current cart for a customer")
    public ResponseEntity<CartOutputDTO> getCart(@PathVariable String customerId) {
        return ResponseEntity.ok(getCartUseCase.execute(customerId));
    }

    @DeleteMapping("/{customerId}/items/{productId}")
    @Operation(summary = "Remove a single product from the cart")
    public ResponseEntity<CartOutputDTO> removeItem(
            @PathVariable String customerId,
            @PathVariable String productId
    ) {
        return ResponseEntity.ok(removeFromCartUseCase.execute(customerId, productId));
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Clear the entire cart (called after order is placed)")
    public ResponseEntity<Void> clearCart(@PathVariable String customerId) {
        clearCartUseCase.execute(customerId);
        return ResponseEntity.noContent().build();
    }
}
