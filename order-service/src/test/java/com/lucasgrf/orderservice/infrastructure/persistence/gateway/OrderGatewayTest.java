package com.lucasgrf.orderservice.infrastructure.persistence.gateway;

import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.valueobject.*;
import com.lucasgrf.orderservice.infrastructure.persistence.mongo.repository.OrderMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@Import(OrderGateway.class)
class OrderGatewayTest {

    @Autowired
    private OrderMongoRepository mongoRepository;

    @Autowired
    private OrderGateway orderGateway;

    @BeforeEach
    void setUp() {
        mongoRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFindOrder() {
        OrderId orderId = new OrderId(UUID.randomUUID().toString());
        Address address = new Address("Street X", "City Y", "State Z", "00000-000");
        Order order = new Order(orderId, "cust_123", address);
        order.setShippingPrice(new Money(new BigDecimal("15.00")));

        orderGateway.save(order);

        Optional<Order> foundOrder = orderGateway.findById(orderId);

        assertTrue(foundOrder.isPresent());
        assertEquals(orderId, foundOrder.get().getId());
        assertEquals("cust_123", foundOrder.get().getCustomerId());
        assertEquals("PENDING_PAYMENT", foundOrder.get().getState().getName());
        assertEquals(new BigDecimal("15.00"), foundOrder.get().getShippingPrice().amount());
    }
}
