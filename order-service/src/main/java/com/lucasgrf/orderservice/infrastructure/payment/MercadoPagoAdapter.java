package com.lucasgrf.orderservice.infrastructure.payment;

import com.lucasgrf.orderservice.application.port.PaymentServicePort;
import com.lucasgrf.orderservice.domain.entity.Order;
import com.lucasgrf.orderservice.domain.entity.OrderItem;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter that bridges the domain PaymentServicePort with the Mercado Pago SDK.
 * Uses Checkout Pro: creates a preference and returns the sandbox init_point URL.
 */
@Slf4j
@Component
public class MercadoPagoAdapter implements PaymentServicePort {

    private final String notificationUrl;
    private final String successUrl;
    private final String failureUrl;
    private final String pendingUrl;

    public MercadoPagoAdapter(
            @Value("${mercadopago.access-token}") String accessToken,
            @Value("${mercadopago.notification-url:http://localhost:8082/payments/webhook}") String notificationUrl,
            @Value("${mercadopago.back-urls.success:http://localhost:3000/payment/success}") String successUrl,
            @Value("${mercadopago.back-urls.failure:http://localhost:3000/payment/failure}") String failureUrl,
            @Value("${mercadopago.back-urls.pending:http://localhost:3000/payment/pending}") String pendingUrl
    ) {
        MercadoPagoConfig.setAccessToken(accessToken);
        this.notificationUrl = notificationUrl;
        this.successUrl = successUrl;
        this.failureUrl = failureUrl;
        this.pendingUrl = pendingUrl;
    }

    @Override
    public String createPaymentPreference(Order order) {
        try {
            List<PreferenceItemRequest> items = order.getItems().stream()
                    .map(this::toPreferenceItem)
                    .collect(Collectors.toList());

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .externalReference(order.getId().value())
                    .items(items)
                    .backUrls(PreferenceBackUrlsRequest.builder()
                            .success(successUrl)
                            .failure(failureUrl)
                            .pending(pendingUrl)
                            .build())
                    .notificationUrl(notificationUrl)
                    .autoReturn("approved")
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            // Returns sandbox_init_point for test tokens, init_point for production
            String url = preference.getSandboxInitPoint() != null
                    ? preference.getSandboxInitPoint()
                    : preference.getInitPoint();

            log.info("Payment preference created for order {}: {}", order.getId().value(), url);
            return url;

        } catch (Exception e) {
            log.error("Failed to create payment preference for order {}", order.getId().value(), e);
            // Fail gracefully — order is already persisted; user can retry payment
            return null;
        }
    }

    private PreferenceItemRequest toPreferenceItem(OrderItem item) {
        return PreferenceItemRequest.builder()
                .id(item.getProductId())
                .title("Produto: " + item.getProductId())
                .quantity(item.getQuantity())
                .unitPrice(item.getPrice().amount())
                .currencyId("BRL")
                .build();
    }
}
