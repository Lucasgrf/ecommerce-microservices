package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.OrderOutputDTO;
import com.lucasgrf.orderservice.domain.exception.DomainException;
import com.lucasgrf.orderservice.domain.repository.OrderRepository;
import com.lucasgrf.orderservice.domain.valueobject.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindOrderByIdUseCase {

    private final OrderRepository orderRepository;

    public OrderOutputDTO execute(String id) {
        return orderRepository.findById(new OrderId(id))
                .map(OrderOutputDTO::from)
                .orElseThrow(() -> new DomainException("Order not found with id: " + id));
    }
}
