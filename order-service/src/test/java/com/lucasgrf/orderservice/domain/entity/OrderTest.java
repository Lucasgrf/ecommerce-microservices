package com.lucasgrf.orderservice.domain.entity;

import com.lucasgrf.orderservice.domain.exception.DomainException;
import com.lucasgrf.orderservice.domain.valueobject.Address;
import com.lucasgrf.orderservice.domain.valueobject.Money;
import com.lucasgrf.orderservice.domain.valueobject.OrderId;
import com.lucasgrf.orderservice.domain.valueobject.TrackingCode;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void shouldCreateNewOrderInPendingPaymentState() {
        OrderId id = new OrderId(UUID.randomUUID().toString()); // assuming String for simplicity
        Address address = new Address("Rua A, 123", "São Paulo", "SP", "01000-000");
        String customerId = "cust_123";

        Order order = new Order(id, customerId, address);

        assertEquals(id, order.getId());
        assertEquals("PENDING_PAYMENT", order.getState().getName());
        assertEquals(Money.zero(), order.getTotal());
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void shouldAddItemsAndCalculateTotal() {
        Order order = new Order(new OrderId("ord_1"), "cust_1", new Address("A", "B", "C", "D"));
        OrderItem item1 = new OrderItem("prod_1", 2, new Money(new BigDecimal("50.00")));
        OrderItem item2 = new OrderItem("prod_2", 1, new Money(new BigDecimal("100.00")));

        order.addItem(item1);
        order.addItem(item2);

        assertEquals(2, order.getItems().size());
        assertEquals(new Money(new BigDecimal("200.00")), order.getTotal());
    }

    @Test
    void shouldTransitionToPaidState() {
        Order order = new Order(new OrderId("ord_1"), "cust_1", new Address("A", "B", "C", "D"));
        order.pay();

        assertEquals("PAID", order.getState().getName());
    }

    @Test
    void shouldTransitionToShippedState() {
        Order order = new Order(new OrderId("ord_1"), "cust_1", new Address("A", "B", "C", "D"));
        order.pay();
        TrackingCode code = new TrackingCode("BR123456789XP");
        order.ship(code);

        assertEquals("SHIPPED", order.getState().getName());
        assertEquals(code, order.getTrackingCode());
    }

    @Test
    void shouldTransitionToDeliveredState() {
        Order order = new Order(new OrderId("ord_1"), "cust_1", new Address("A", "B", "C", "D"));
        order.pay();
        order.ship(new TrackingCode("BR123456789XP"));
        order.deliver();

        assertEquals("DELIVERED", order.getState().getName());
    }

    @Test
    void shouldTransitionToCancelledState() {
        Order order = new Order(new OrderId("ord_1"), "cust_1", new Address("A", "B", "C", "D"));
        order.cancel("Customer requested cancellation");

        assertEquals("CANCELLED", order.getState().getName());
    }

    @Test
    void shouldNotAllowShippingMWhenNotPaid() {
        Order order = new Order(new OrderId("ord_1"), "cust_1", new Address("A", "B", "C", "D"));
        TrackingCode code = new TrackingCode("BR123456789XP");

        DomainException exception = assertThrows(DomainException.class, () -> order.ship(code));
        assertEquals("Order cannot be shipped from current state: PENDING_PAYMENT", exception.getMessage());
    }
}
