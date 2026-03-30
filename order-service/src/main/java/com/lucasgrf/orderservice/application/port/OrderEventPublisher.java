package com.lucasgrf.orderservice.application.port;

import com.lucasgrf.orderservice.application.dto.OrderEvent;

public interface OrderEventPublisher {
    void publish(OrderEvent event);
}
