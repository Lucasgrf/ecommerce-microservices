package com.lucasgrf.orderservice.domain.entity.state;

public class DeliveredState implements OrderState {

    @Override
    public String getName() {
        return "DELIVERED";
    }
}
