package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.OrderOutputDTO;
import com.lucasgrf.orderservice.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListOrdersUseCase {

    private final OrderRepository orderRepository;

    public Page<OrderOutputDTO> execute(String customerId, Pageable pageable) {
        if (customerId != null && !customerId.isBlank()) {
            return orderRepository.findByCustomerId(customerId, pageable).map(OrderOutputDTO::from);
        }
        return orderRepository.findAll(pageable).map(OrderOutputDTO::from);
    }
}
