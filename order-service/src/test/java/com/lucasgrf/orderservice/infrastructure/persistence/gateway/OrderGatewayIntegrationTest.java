package com.lucasgrf.orderservice.infrastructure.persistence.gateway;

import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.valueobject.*;
import com.lucasgrf.orderservice.infrastructure.persistence.mongo.repository.OrderMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for OrderGateway against a real MongoDB instance via Testcontainers.
 * This replaces the existing OrderGatewayTest which assumed a local MongoDB on localhost.
 */
@DataMongoTest
@Testcontainers
@Import(OrderGateway.class)
@DisplayName("Order Gateway - MongoDB Integration Tests")
class OrderGatewayIntegrationTest {

    @Container
    @SuppressWarnings("resource")
    static MongoDBContainer mongodb = new MongoDBContainer("mongo:6")
            .withExposedPorts(27017);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongodb::getReplicaSetUrl);
    }

    @Autowired
    private OrderMongoRepository mongoRepository;

    @Autowired
    private OrderGateway orderGateway;

    @BeforeEach
    void setUp() {
        mongoRepository.deleteAll();
    }

    @Test
    @DisplayName("Should persist and retrieve an order by ID")
    void shouldPersistAndRetrieveOrderById() {
        // given
        OrderId orderId = new OrderId(UUID.randomUUID().toString());
        Address address = new Address("Rua das Flores", "São Paulo", "SP", "01310-200");
        Order order = new Order(orderId, "customer-001", address);
        order.setShippingPrice(new Money(new BigDecimal("25.00")));

        // when
        orderGateway.save(order);
        Optional<Order> found = orderGateway.findById(orderId);

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(orderId);
        assertThat(found.get().getCustomerId()).isEqualTo("customer-001");
        assertThat(found.get().getShippingPrice().amount()).isEqualByComparingTo(new BigDecimal("25.00"));
    }

    @Test
    @DisplayName("Should return empty when order not found by ID")
    void shouldReturnEmptyWhenOrderNotFound() {
        // when
        Optional<Order> found = orderGateway.findById(new OrderId(UUID.randomUUID().toString()));

        // then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should list all orders for a specific customer")
    void shouldListAllOrdersForCustomer() {
        // given
        Address address = new Address("Av Brasil", "Rio de Janeiro", "RJ", "20040-020");
        String customerId = "customer-xyz";

        orderGateway.save(new Order(new OrderId(UUID.randomUUID().toString()), customerId, address));
        orderGateway.save(new Order(new OrderId(UUID.randomUUID().toString()), customerId, address));
        orderGateway.save(new Order(new OrderId(UUID.randomUUID().toString()), "other-customer", address));

        // when
        List<Order> orders = orderGateway.findByCustomerId(customerId,
                org.springframework.data.domain.PageRequest.of(0, 10)).getContent();

        // then
        assertThat(orders).hasSize(2);
        assertThat(orders).allMatch(o -> customerId.equals(o.getCustomerId()));
    }

    @Test
    @DisplayName("New order should start in PENDING_PAYMENT state")
    void newOrderShouldBeInPendingPaymentState() {
        // given
        OrderId orderId = new OrderId(UUID.randomUUID().toString());
        Order order = new Order(orderId, "customer-state-test",
                new Address("R. Test", "Cidade", "UF", "00000-000"));

        // when
        orderGateway.save(order);
        Order found = orderGateway.findById(orderId).orElseThrow();

        // then
        assertThat(found.getState().getName()).isEqualTo("PENDING_PAYMENT");
    }
}
