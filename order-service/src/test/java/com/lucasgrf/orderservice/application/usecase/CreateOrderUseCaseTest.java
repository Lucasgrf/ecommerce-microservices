package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.CreateOrderInputDTO;
import com.lucasgrf.orderservice.application.dto.OrderItemInputDTO;
import com.lucasgrf.orderservice.application.dto.OrderOutputDTO;
import com.lucasgrf.orderservice.application.dto.ProductResponseDTO;
import com.lucasgrf.orderservice.application.dto.ShippingQuoteDTO;
import com.lucasgrf.orderservice.application.port.OrderEventPublisher;
import com.lucasgrf.orderservice.application.port.ProductServicePort;
import com.lucasgrf.orderservice.application.port.ShippingServicePort;
import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductServicePort productServicePort;

    @Mock
    private OrderEventPublisher orderEventPublisher;

    @Mock
    private ShippingServicePort shippingServicePort;

    @InjectMocks
    private CreateOrderUseCase createOrderUseCase;

    @Test
    void shouldCreateOrder() {
        CreateOrderInputDTO input = new CreateOrderInputDTO(
                "cust_1", "Rua X", "City Y", "SP", "00000-000",
                List.of(new OrderItemInputDTO("prod_1", 2, new BigDecimal("50.00")))
        );

        ProductResponseDTO productMock = new ProductResponseDTO(
                "prod_1", "Product 1", "Desc", new BigDecimal("50.00"),
                "cat_1", 10, List.of(), true,
                0.5, 10.0, 5.0, 15.0
        );

        when(productServicePort.getProductById("prod_1")).thenReturn(Optional.of(productMock));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(shippingServicePort.calculateShipping(any(), any())).thenReturn(Optional.of(new ShippingQuoteDTO(
                "1", "PAC", new BigDecimal("15.00"), 5, "Correios"
        )));

        OrderOutputDTO output = createOrderUseCase.execute(input);

        assertNotNull(output);
        assertEquals("cust_1", output.customerId());
        assertEquals("PENDING_PAYMENT", output.state());
        assertEquals(new BigDecimal("115.00"), output.total());
        assertEquals(1, output.items().size());

        verify(productServicePort).getProductById("prod_1");
        verify(orderRepository).save(any(Order.class));
        verify(orderEventPublisher).publish(any());
    }
}
