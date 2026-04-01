package com.lucasgrf.notificationservice.domain.entity;

import com.lucasgrf.notificationservice.domain.valueobject.EmailAddress;

import java.util.Map;

public record Notification(
        EmailAddress recipient,
        String subject,
        String templateName,
        Map<String, Object> templateContext
) {}
