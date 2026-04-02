package com.lucasgrf.notificationservice.infrastructure.messaging.consumer;

import com.lucasgrf.notificationservice.application.dto.OrderEvent;
import com.lucasgrf.notificationservice.application.usecase.SendOrderConfirmationUseCase;
import com.lucasgrf.notificationservice.infrastructure.messaging.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final SendOrderConfirmationUseCase sendOrderConfirmationUseCase;

    @RabbitListener(queues = RabbitConfig.ORDER_CREATED_QUEUE)
    public void consumeOrderCreated(OrderEvent event) {
        log.info("Received order created event for orderId: {}", event.orderId());
        try {
            sendOrderConfirmationUseCase.execute(event);
            log.info("Successfully processed order created event for orderId: {}", event.orderId());
        } catch (Exception e) {
            log.error("Error processing order created event for orderId: {}", event.orderId(), e);
            throw e; // will trigger retry or push to DLQ depending on spring AMQP config
        }
    }
}
