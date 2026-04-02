package com.lucasgrf.orderservice.domain.entity.state;

import lombok.Getter;

@Getter
public class CancelledState implements OrderState {

    private final String reason;

    public CancelledState(String reason) {
        this.reason = reason;
    }

    @Override
    public String getName() {
        return "CANCELLED";
    }
}
