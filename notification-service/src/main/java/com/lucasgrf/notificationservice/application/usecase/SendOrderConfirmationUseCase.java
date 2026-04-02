package com.lucasgrf.notificationservice.application.usecase;

import com.lucasgrf.notificationservice.application.dto.OrderEvent;
import com.lucasgrf.notificationservice.domain.entity.Notification;
import com.lucasgrf.notificationservice.domain.port.EmailSenderPort;
import com.lucasgrf.notificationservice.domain.valueobject.EmailAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SendOrderConfirmationUseCase {

    private final EmailSenderPort emailSenderPort;

    public void execute(OrderEvent event) {
        EmailAddress recipient = new EmailAddress(event.customerEmail());
        String subject = "Order Confirmation - " + event.orderId();
        
        Map<String, Object> context = new HashMap<>();
        context.put("order", event);

        Notification notification = new Notification(
                recipient,
                subject,
                "order-confirmation",
                context
        );

        emailSenderPort.send(notification);
    }
}
