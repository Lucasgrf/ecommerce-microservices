package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.CreateOrderInputDTO;
import com.lucasgrf.orderservice.application.dto.OrderItemOutputDTO;
import com.lucasgrf.orderservice.application.dto.OrderOutputDTO;
import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.entity.OrderItem;
import com.lucasgrf.orderservice.domain.repository.OrderRepository;
import com.lucasgrf.orderservice.domain.valueobject.Address;
import com.lucasgrf.orderservice.domain.valueobject.Money;
import com.lucasgrf.orderservice.domain.valueobject.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;

    public OrderOutputDTO execute(CreateOrderInputDTO input) {
        OrderId orderId = new OrderId(UUID.randomUUID().toString());
        Address address = new Address(input.street(), input.city(), input.state(), input.zipCode());

        Order order = new Order(orderId, input.customerId(), address);

        input.items().forEach(itemDto -> {
            OrderItem item = new OrderItem(itemDto.productId(), itemDto.quantity(), new Money(itemDto.price()));
            order.addItem(item);
        });

        Order savedOrder = orderRepository.save(order);

        List<OrderItemOutputDTO> itemOutputs = savedOrder.getItems().stream()
                .map(i -> new OrderItemOutputDTO(
                        i.getProductId(),
                        i.getQuantity(),
                        i.getPrice().amount(),
                        i.getTotal().amount()
                )).collect(Collectors.toList());

        return new OrderOutputDTO(
                savedOrder.getId().value(),
                savedOrder.getCustomerId(),
                savedOrder.getState().getName(),
                savedOrder.getTotal().amount(),
                savedOrder.getTrackingCode() != null ? savedOrder.getTrackingCode().code() : null,
                itemOutputs
        );
    }
}
