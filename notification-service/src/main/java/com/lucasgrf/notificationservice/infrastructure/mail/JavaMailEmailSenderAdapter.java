package com.lucasgrf.notificationservice.infrastructure.mail;

import com.lucasgrf.notificationservice.domain.entity.Notification;
import com.lucasgrf.notificationservice.domain.exception.DomainException;
import com.lucasgrf.notificationservice.domain.port.EmailSenderPort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class JavaMailEmailSenderAdapter implements EmailSenderPort {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void send(Notification notification) {
        try {
            Context context = new Context();
            context.setVariables(notification.templateContext());

            String htmlBody = templateEngine.process(notification.templateName(), context);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(notification.recipient().value());
            helper.setSubject(notification.subject());
            helper.setText(htmlBody, true);

            javaMailSender.send(message);
            log.info("Sent email to {} with subject: {}", notification.recipient().value(), notification.subject());

        } catch (MessagingException e) {
            log.error("Failed to send email to " + notification.recipient().value(), e);
            throw new DomainException("Failed to send email");
        }
    }
}
