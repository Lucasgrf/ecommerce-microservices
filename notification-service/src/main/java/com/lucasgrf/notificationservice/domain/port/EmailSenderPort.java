package com.lucasgrf.notificationservice.domain.port;

import com.lucasgrf.notificationservice.domain.entity.Notification;

public interface EmailSenderPort {
    void send(Notification notification);
}
