package com.lucasgrf.orderservice.infrastructure.messaging.publisher;

import com.lucasgrf.orderservice.application.dto.OrderEvent;
import com.lucasgrf.orderservice.application.port.OrderEventPublisher;
import com.lucasgrf.orderservice.infrastructure.messaging.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitOrderEventPublisher implements OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(OrderEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.ORDER_EXCHANGE,
                RabbitConfig.ORDER_CREATED_ROUTING_KEY,
                event
        );
    }
}
