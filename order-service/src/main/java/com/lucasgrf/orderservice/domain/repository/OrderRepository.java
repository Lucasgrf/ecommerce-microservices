package com.lucasgrf.orderservice.domain.repository;

import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.valueobject.OrderId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId id);
}
