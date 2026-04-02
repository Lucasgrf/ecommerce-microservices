package com.lucasgrf.orderservice.domain.entity.state;

import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.valueobject.TrackingCode;

public class PaidState implements OrderState {

    @Override
    public String getName() {
        return "PAID";
    }

    @Override
    public void ship(Order order, TrackingCode trackingCode) {
        order.setTrackingCode(trackingCode);
        order.changeState(new ShippedState());
    }

    @Override
    public void cancel(Order order, String reason) {
        // Here we could emit a Refund event
        order.changeState(new CancelledState(reason));
    }
}
