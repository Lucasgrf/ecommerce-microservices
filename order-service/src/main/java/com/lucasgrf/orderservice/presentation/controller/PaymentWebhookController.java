package com.lucasgrf.orderservice.presentation.controller;

import com.lucasgrf.orderservice.application.dto.UpdateOrderStatusInputDTO;
import com.lucasgrf.orderservice.application.usecase.UpdateOrderStatusUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Receives payment status notifications from Mercado Pago IPN/Webhook.
 * The endpoint must be publicly accessible (e.g., via Ngrok in local dev).
 *
 * MP sends a POST with a body containing { "topic": "...", "resource": "..." }
 * or { "type": "payment", "data": { "id": "..." } } depending on the version.
 */
@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    /**
     * Handles Mercado Pago IPN notifications.
     *
     * MP sends the external_reference that we set as the Order ID when creating the preference.
     * The webhook body structure (V1 IPN):
     * {
     *   "topic": "payment",
     *   "resource": "/v1/payments/{id}"
     * }
     *
     * For simplicity in this MVP, we use the `external_reference` sent in the body.
     * In production, verify the payment via MP API before processing.
     */
    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody Map<String, Object> payload) {
        log.info("Received Mercado Pago webhook: {}", payload);

        try {
            String orderId = extractOrderId(payload);
            if (orderId == null) {
                log.warn("Webhook received without a valid orderId. Payload: {}", payload);
                return ResponseEntity.ok().build(); // Always return 200 to stop MP retries
            }

            String topic = (String) payload.getOrDefault("topic", payload.getOrDefault("type", ""));

            // Only process approved payment notifications
            if ("payment".equals(topic)) {
                log.info("Processing payment approval for order: {}", orderId);
                updateOrderStatusUseCase.execute(new UpdateOrderStatusInputDTO(
                        orderId,
                        UpdateOrderStatusInputDTO.OrderCommand.PAY,
                        null,
                        null
                ));
            }
        } catch (Exception e) {
            // Log the error but always return 200 to prevent MP from retrying indefinitely
            log.error("Error processing Mercado Pago webhook", e);
        }

        return ResponseEntity.ok().build();
    }

    /**
     * Extracts the order ID from the webhook payload.
     * MP can send it as 'external_reference' directly or nested in 'data'.
     */
    private String extractOrderId(Map<String, Object> payload) {
        // Attempt 1: direct external_reference (some IPN formats)
        if (payload.containsKey("external_reference")) {
            return (String) payload.get("external_reference");
        }
        // Attempt 2: nested in 'data' map
        if (payload.containsKey("data") && payload.get("data") instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) payload.get("data");
            if (data.containsKey("external_reference")) {
                return (String) data.get("external_reference");
            }
        }
        return null;
    }
}
