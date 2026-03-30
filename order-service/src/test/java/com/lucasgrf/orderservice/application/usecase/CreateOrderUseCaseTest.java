package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.CreateOrderInputDTO;
import com.lucasgrf.orderservice.application.dto.OrderItemInputDTO;
import com.lucasgrf.orderservice.application.dto.OrderOutputDTO;
import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CreateOrderUseCase createOrderUseCase;

    @Test
    void shouldCreateOrder() {
        CreateOrderInputDTO input = new CreateOrderInputDTO(
                "cust_1", "Rua X", "City Y", "SP", "00000-000",
                List.of(new OrderItemInputDTO("prod_1", 2, new BigDecimal("50.00")))
        );

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderOutputDTO output = createOrderUseCase.execute(input);

        assertNotNull(output);
        assertEquals("cust_1", output.customerId());
        assertEquals("PENDING_PAYMENT", output.state());
        assertEquals(new BigDecimal("100.00"), output.total());
        assertEquals(1, output.items().size());

        verify(orderRepository).save(any(Order.class));
    }
}
