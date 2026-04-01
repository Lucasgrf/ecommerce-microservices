package com.lucasgrf.notificationservice.application.usecase;

import com.lucasgrf.notificationservice.application.dto.OrderEvent;
import com.lucasgrf.notificationservice.domain.entity.Notification;
import com.lucasgrf.notificationservice.domain.port.EmailSenderPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SendOrderConfirmationUseCaseTest {

    @Mock
    private EmailSenderPort emailSenderPort;

    @InjectMocks
    private SendOrderConfirmationUseCase sendOrderConfirmationUseCase;

    @Test
    void shouldFormatNotificationAndSendEmail() {
        // Arrange
        OrderEvent event = new OrderEvent(
                "order-123",
                "customer-001",
                "test@example.com",
                new BigDecimal("150.00"),
                List.of(
                        new OrderEvent.OrderEventItem("prod-abc", 1, new BigDecimal("150.00"))
                )
        );

        // Act
        sendOrderConfirmationUseCase.execute(event);

        // Assert
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(emailSenderPort).send(notificationCaptor.capture());

        Notification captured = notificationCaptor.getValue();
        assertEquals("test@example.com", captured.recipient().value());
        assertEquals("Order Confirmation - order-123", captured.subject());
        assertEquals("order-confirmation", captured.templateName());

        Map<String, Object> context = captured.templateContext();
        assertEquals(event, context.get("order"));
    }
}
