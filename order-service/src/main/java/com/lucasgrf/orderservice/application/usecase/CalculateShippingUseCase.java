package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.ShippingRateDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CalculateShippingUseCase {

    public List<ShippingRateDTO> execute(String zipCode) {
        // Mocking "Melhor Envio" response
        return List.of(
                new ShippingRateDTO("SEDEX", new BigDecimal("25.50"), 2, "Correios"),
                new ShippingRateDTO("PAC", new BigDecimal("15.20"), 5, "Correios"),
                new ShippingRateDTO("Standard", new BigDecimal("12.00"), 7, "Jadlog")
        );
    }
}
