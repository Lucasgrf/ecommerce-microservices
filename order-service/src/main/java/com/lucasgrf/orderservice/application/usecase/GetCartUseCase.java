package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.CartOutputDTO;
import com.lucasgrf.orderservice.domain.entity.Cart;
import com.lucasgrf.orderservice.domain.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCartUseCase {

    private final CartRepository cartRepository;

    public CartOutputDTO execute(String customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElse(new Cart(customerId)); // empty cart — no need to persist
        return AddToCartUseCase.toOutput(cart);
    }
}
