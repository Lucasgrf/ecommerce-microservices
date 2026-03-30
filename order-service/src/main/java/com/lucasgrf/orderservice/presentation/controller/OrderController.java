package com.lucasgrf.orderservice.presentation.controller;

import com.lucasgrf.orderservice.application.dto.CreateOrderInputDTO;
import com.lucasgrf.orderservice.application.dto.OrderOutputDTO;
import com.lucasgrf.orderservice.application.dto.UpdateOrderStatusInputDTO;
import com.lucasgrf.orderservice.application.usecase.CreateOrderUseCase;
import com.lucasgrf.orderservice.application.usecase.FindOrderByIdUseCase;
import com.lucasgrf.orderservice.application.usecase.ListOrdersUseCase;
import com.lucasgrf.orderservice.application.usecase.UpdateOrderStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final ListOrdersUseCase listOrdersUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @PostMapping
    public ResponseEntity<OrderOutputDTO> createOrder(@RequestBody CreateOrderInputDTO input) {
        OrderOutputDTO output = createOrderUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutputDTO> getOrderById(@PathVariable String id) {
        return ResponseEntity.ok(findOrderByIdUseCase.execute(id));
    }

    @GetMapping
    public ResponseEntity<Page<OrderOutputDTO>> listOrders(
            @RequestParam(required = false) String customerId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(listOrdersUseCase.execute(customerId, pageable));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderOutputDTO> updateStatus(
            @PathVariable String id,
            @RequestBody UpdateOrderStatusInputDTO input
    ) {
        // Ensure the ID in path matches the body or just override it
        UpdateOrderStatusInputDTO unifiedInput = new UpdateOrderStatusInputDTO(
                id,
                input.command(),
                input.reason(),
                input.trackingCode()
        );
        return ResponseEntity.ok(updateOrderStatusUseCase.execute(unifiedInput));
    }
}
