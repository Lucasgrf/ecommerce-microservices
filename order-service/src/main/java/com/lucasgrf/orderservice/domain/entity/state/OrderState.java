package com.lucasgrf.orderservice.domain.entity.state;

import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.exception.DomainException;
import com.lucasgrf.orderservice.domain.valueobject.TrackingCode;

public interface OrderState {
    
    String getName();

    default void pay(Order order) {
        throw new DomainException("Order cannot be paid from current state: " + getName());
    }

    default void ship(Order order, TrackingCode trackingCode) {
        throw new DomainException("Order cannot be shipped from current state: " + getName());
    }

    default void deliver(Order order) {
        throw new DomainException("Order cannot be delivered from current state: " + getName());
    }

    default void cancel(Order order, String reason) {
        throw new DomainException("Order cannot be cancelled from current state: " + getName());
    }
}
