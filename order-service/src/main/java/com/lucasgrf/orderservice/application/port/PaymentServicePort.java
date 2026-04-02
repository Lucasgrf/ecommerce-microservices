package com.lucasgrf.orderservice.application.port;

import com.lucasgrf.orderservice.domain.entity.Order;

/**
 * Output port: defines the contract for creating a payment preference.
 * The domain depends on this interface, never on the concrete adapter.
 */
public interface PaymentServicePort {
    /**
     * Creates a payment preference and returns the checkout URL (init_point).
     */
    String createPaymentPreference(Order order);
}
