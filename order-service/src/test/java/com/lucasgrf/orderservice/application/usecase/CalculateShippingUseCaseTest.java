package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.ShippingRateDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CalculateShippingUseCaseTest {

    private final CalculateShippingUseCase calculateShippingUseCase = new CalculateShippingUseCase();

    @Test
    void shouldReturnMockedShippingRates() {
        String zipCode = "01000-000";
        
        List<ShippingRateDTO> rates = calculateShippingUseCase.execute(zipCode);

        assertNotNull(rates);
        assertFalse(rates.isEmpty());
        rates.forEach(rate -> {
            assertNotNull(rate.serviceName());
            assertNotNull(rate.carrier());
            assertNotNull(rate.price());
            assertFalse(rate.price().compareTo(BigDecimal.ZERO) < 0);
        });
    }
}
