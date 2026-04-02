package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.domain.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClearCartUseCase {

    private final CartRepository cartRepository;

    public void execute(String customerId) {
        cartRepository.deleteByCustomerId(customerId);
    }
}
