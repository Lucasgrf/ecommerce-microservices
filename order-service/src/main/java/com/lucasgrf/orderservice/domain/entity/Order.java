package com.lucasgrf.orderservice.domain.entity;

import com.lucasgrf.orderservice.domain.entity.state.OrderState;
import com.lucasgrf.orderservice.domain.entity.state.PendingPaymentState;
import com.lucasgrf.orderservice.domain.valueobject.Address;
import com.lucasgrf.orderservice.domain.valueobject.Money;
import com.lucasgrf.orderservice.domain.valueobject.OrderId;
import com.lucasgrf.orderservice.domain.valueobject.TrackingCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Order {

    private final OrderId id;
    private final String customerId;
    private final Address shippingAddress;
    private final List<OrderItem> items;
    private OrderState state;
    private Money shippingPrice;
    
    @Setter
    private TrackingCode trackingCode;

    public Order(OrderId id, String customerId, Address shippingAddress) {
        this.id = id;
        this.customerId = customerId;
        this.shippingAddress = shippingAddress;
        this.items = new ArrayList<>();
        this.state = new PendingPaymentState();
        this.shippingPrice = Money.zero();
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Money getTotal() {
        Money total = Money.zero();
        for (OrderItem item : items) {
            total = total.add(item.getTotal());
        }
        return total.add(this.shippingPrice);
    }

    public void setShippingPrice(Money shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public void changeState(OrderState newState) {
        this.state = newState;
    }

    public void pay() {
        state.pay(this);
    }

    public void ship(TrackingCode code) {
        state.ship(this, code);
    }

    public void deliver() {
        state.deliver(this);
    }

    public void cancel(String reason) {
        state.cancel(this, reason);
    }
}
