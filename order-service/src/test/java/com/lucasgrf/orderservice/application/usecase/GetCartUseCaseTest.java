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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCartUseCaseTest {

    @Mock private CartRepository cartRepository;
    @InjectMocks private GetCartUseCase getCartUseCase;

    @Test
    void shouldReturnCartWithItemsWhenExists() {
        Cart cart = new Cart("user-1");
        cart.addItem(new CartItem("prod-A", "Shirt", 3, new Money(new BigDecimal("50.00"))));
        when(cartRepository.findByCustomerId("user-1")).thenReturn(Optional.of(cart));

        CartOutputDTO result = getCartUseCase.execute("user-1");

        assertEquals("user-1", result.customerId());
        assertEquals(1, result.items().size());
        assertEquals(new BigDecimal("150.00"), result.total());
    }

    @Test
    void shouldReturnEmptyCartWhenNoneExists() {
        when(cartRepository.findByCustomerId("ghost-user")).thenReturn(Optional.empty());

        CartOutputDTO result = getCartUseCase.execute("ghost-user");

        assertEquals("ghost-user", result.customerId());
        assertTrue(result.items().isEmpty());
        assertEquals(BigDecimal.ZERO.setScale(2), result.total());
    }
}
