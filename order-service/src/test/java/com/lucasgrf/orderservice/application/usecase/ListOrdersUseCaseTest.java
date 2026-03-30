package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.OrderOutputDTO;
import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.entity.state.PendingPaymentState;
import com.lucasgrf.orderservice.domain.repository.OrderRepository;
import com.lucasgrf.orderservice.domain.valueobject.Address;
import com.lucasgrf.orderservice.domain.valueobject.Money;
import com.lucasgrf.orderservice.domain.valueobject.OrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ListOrdersUseCaseTest {

    private ListOrdersUseCase listOrdersUseCase;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listOrdersUseCase = new ListOrdersUseCase(orderRepository);
    }

    @Test
    void shouldListAllOrders() {
        OrderId orderId = new OrderId("123");
        Address address = new Address("Street", "City", "ST", "00000");
        Order order = new Order(orderId, "cust_123", address);
        order.setShippingPrice(new Money(new BigDecimal("10.0")));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> orderPage = new PageImpl<>(List.of(order), pageable, 1);

        when(orderRepository.findAll(any(Pageable.class))).thenReturn(orderPage);

        Page<OrderOutputDTO> result = listOrdersUseCase.execute(null, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("123", result.getContent().get(0).id());
        assertEquals("cust_123", result.getContent().get(0).customerId());
    }

    @Test
    void shouldListOrdersByCustomerId() {
        OrderId orderId = new OrderId("123");
        Address address = new Address("Street", "City", "ST", "00000");
        Order order = new Order(orderId, "cust_123", address);
        order.setShippingPrice(new Money(new BigDecimal("10.0")));

        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> orderPage = new PageImpl<>(List.of(order), pageable, 1);

        when(orderRepository.findByCustomerId(eq("cust_123"), any(Pageable.class))).thenReturn(orderPage);

        Page<OrderOutputDTO> result = listOrdersUseCase.execute("cust_123", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("cust_123", result.getContent().get(0).customerId());
    }
}
