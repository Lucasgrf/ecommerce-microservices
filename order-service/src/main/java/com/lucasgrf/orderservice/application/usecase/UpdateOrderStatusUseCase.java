package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.OrderItemOutputDTO;
import com.lucasgrf.orderservice.application.dto.OrderOutputDTO;
import com.lucasgrf.orderservice.application.dto.UpdateOrderStatusInputDTO;
import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.exception.DomainException;
import com.lucasgrf.orderservice.domain.repository.OrderRepository;
import com.lucasgrf.orderservice.domain.valueobject.OrderId;
import com.lucasgrf.orderservice.domain.valueobject.TrackingCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateOrderStatusUseCase {

    private final OrderRepository orderRepository;

    public OrderOutputDTO execute(UpdateOrderStatusInputDTO input) {
        Order order = orderRepository.findById(new OrderId(input.orderId()))
                .orElseThrow(() -> new DomainException("Order not found with id: " + input.orderId()));

        switch (input.command()) {
            case PAY -> order.pay();
            case SHIP -> {
                if(input.trackingCode() == null) throw new DomainException("Tracking Code is required for SHIP command");
                order.ship(new TrackingCode(input.trackingCode()));
            }
            case DELIVER -> order.deliver();
            case CANCEL -> order.cancel(input.reason());
        }

        Order savedOrder = orderRepository.save(order);

        return new OrderOutputDTO(
                savedOrder.getId().value(),
                savedOrder.getCustomerId(),
                savedOrder.getState().getName(),
                savedOrder.getTotal().amount(),
                savedOrder.getTrackingCode() != null ? savedOrder.getTrackingCode().code() : null,
                savedOrder.getItems().stream()
                        .map(i -> new OrderItemOutputDTO(i.getProductId(), i.getQuantity(), i.getPrice().amount(), i.getTotal().amount()))
                        .collect(Collectors.toList())
        );
    }
}
