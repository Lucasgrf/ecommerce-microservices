package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.OrderOutputDTO;
import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.exception.DomainException;
import com.lucasgrf.orderservice.domain.repository.OrderRepository;
import com.lucasgrf.orderservice.domain.valueobject.Address;
import com.lucasgrf.orderservice.domain.valueobject.Money;
import com.lucasgrf.orderservice.domain.valueobject.OrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FindOrderByIdUseCaseTest {

    private FindOrderByIdUseCase findOrderByIdUseCase;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        findOrderByIdUseCase = new FindOrderByIdUseCase(orderRepository);
    }

    @Test
    void shouldFindOrderById() {
        OrderId orderId = new OrderId("123");
        Address address = new Address("Street", "City", "ST", "00000");
        Order order = new Order(orderId, "cust_123", address);
        order.setShippingPrice(new Money(new BigDecimal("10.0")));

        when(orderRepository.findById(any(OrderId.class))).thenReturn(Optional.of(order));

        OrderOutputDTO result = findOrderByIdUseCase.execute("123");

        assertNotNull(result);
        assertEquals("123", result.id());
        assertEquals("cust_123", result.customerId());
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        when(orderRepository.findById(any(OrderId.class))).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> findOrderByIdUseCase.execute("999"));
    }
}
