package com.lucasgrf.orderservice.domain.entity.state;

import com.lucasgrf.orderservice.domain.entity.Order;

public class PendingPaymentState implements OrderState {

    @Override
    public String getName() {
        return "PENDING_PAYMENT";
    }

    @Override
    public void pay(Order order) {
        order.changeState(new PaidState());
    }

    @Override
    public void cancel(Order order, String reason) {
        order.changeState(new CancelledState(reason));
    }
}
