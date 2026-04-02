package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.UpdateOrderStatusInputDTO;
import com.lucasgrf.orderservice.application.dto.OrderOutputDTO;
import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.exception.DomainException;
import com.lucasgrf.orderservice.domain.repository.OrderRepository;
import com.lucasgrf.orderservice.domain.valueobject.Address;
import com.lucasgrf.orderservice.domain.valueobject.OrderId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateOrderStatusUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @Test
    void shouldPayOrder() {
        OrderId id = new OrderId("ord_1");
        Order order = new Order(id, "cust_1", new Address("R", "C", "S", "Z"));
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        UpdateOrderStatusInputDTO input = new UpdateOrderStatusInputDTO("ord_1", UpdateOrderStatusInputDTO.OrderCommand.PAY, null, null);

        OrderOutputDTO output = updateOrderStatusUseCase.execute(input);

        assertEquals("PAID", output.state());
        verify(orderRepository).save(order);
    }

    @Test
    void shouldShipOrder() {
        OrderId id = new OrderId("ord_1");
        Order order = new Order(id, "cust_1", new Address("R", "C", "S", "Z"));
        order.pay();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        UpdateOrderStatusInputDTO input = new UpdateOrderStatusInputDTO("ord_1", UpdateOrderStatusInputDTO.OrderCommand.SHIP, "BR123456789XP", null);

        OrderOutputDTO output = updateOrderStatusUseCase.execute(input);

        assertEquals("SHIPPED", output.state());
        assertEquals("BR123456789XP", output.trackingCode());
    }

    @Test
    void shouldThrowNotFound() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        UpdateOrderStatusInputDTO input = new UpdateOrderStatusInputDTO("ord_2", UpdateOrderStatusInputDTO.OrderCommand.PAY, null, null);

        assertThrows(DomainException.class, () -> updateOrderStatusUseCase.execute(input));
    }
}
