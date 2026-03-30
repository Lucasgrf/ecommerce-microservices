package com.lucasgrf.orderservice.domain.entity.state;

import com.lucasgrf.orderservice.domain.entity.Order;

public class ShippedState implements OrderState {

    @Override
    public String getName() {
        return "SHIPPED";
    }

    @Override
    public void deliver(Order order) {
        order.changeState(new DeliveredState());
    }
}
