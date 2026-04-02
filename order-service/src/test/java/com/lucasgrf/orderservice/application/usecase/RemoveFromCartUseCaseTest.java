package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.CartOutputDTO;
import com.lucasgrf.orderservice.domain.entity.Cart;
import com.lucasgrf.orderservice.domain.entity.CartItem;
import com.lucasgrf.orderservice.domain.repository.CartRepository;
import com.lucasgrf.orderservice.domain.valueobject.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveFromCartUseCaseTest {

    @Mock private CartRepository cartRepository;
    @InjectMocks private RemoveFromCartUseCase removeFromCartUseCase;

    @Test
    void shouldRemoveItemFromCart() {
        Cart cart = new Cart("user-1");
        cart.addItem(new CartItem("prod-A", "Shirt", 2, new Money(new BigDecimal("50.00"))));
        cart.addItem(new CartItem("prod-B", "Pants", 1, new Money(new BigDecimal("100.00"))));
        when(cartRepository.findByCustomerId("user-1")).thenReturn(Optional.of(cart));
        when(cartRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CartOutputDTO result = removeFromCartUseCase.execute("user-1", "prod-A");

        assertEquals(1, result.items().size());
        assertEquals("prod-B", result.items().get(0).productId());
    }

    @Test
    void shouldReturnEmptyCartWhenNoneExists() {
        when(cartRepository.findByCustomerId("ghost")).thenReturn(Optional.empty());
        when(cartRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CartOutputDTO result = removeFromCartUseCase.execute("ghost", "prod-X");

        assertTrue(result.items().isEmpty());
    }
}
