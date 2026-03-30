package com.lucasgrf.orderservice.domain.repository;

import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.valueobject.OrderId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId id);
    Page<Order> findAll(Pageable pageable);
    Page<Order> findByCustomerId(String customerId, Pageable pageable);
}
