package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.application.dto.AddToCartInputDTO;
import com.lucasgrf.orderservice.application.dto.CartOutputDTO;
import com.lucasgrf.orderservice.domain.entity.Cart;
import com.lucasgrf.orderservice.domain.repository.CartRepository;
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
class AddToCartUseCaseTest {

    @Mock private CartRepository cartRepository;
    @InjectMocks private AddToCartUseCase addToCartUseCase;

    private final String CUSTOMER_ID = "user-abc";

    @Test
    void shouldAddNewItemToEmptyCart() {
        // Arrange
        when(cartRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(inv -> inv.getArgument(0));

        AddToCartInputDTO input = new AddToCartInputDTO(CUSTOMER_ID, "prod-1", "Laptop", 2, new BigDecimal("3000.00"));

        // Act
        CartOutputDTO result = addToCartUseCase.execute(input);

        // Assert
        assertEquals(CUSTOMER_ID, result.customerId());
        assertEquals(1, result.items().size());
        assertEquals(2, result.items().get(0).quantity());
        assertEquals(new BigDecimal("6000.00"), result.total());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void shouldMergeQuantityWhenProductAlreadyInCart() {
        // Arrange
        Cart existingCart = new Cart(CUSTOMER_ID);
        existingCart.addItem(new com.lucasgrf.orderservice.domain.entity.CartItem(
                "prod-1", "Laptop", 1, new com.lucasgrf.orderservice.domain.valueobject.Money(new BigDecimal("3000.00"))
        ));
        when(cartRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.of(existingCart));
        when(cartRepository.save(any(Cart.class))).thenAnswer(inv -> inv.getArgument(0));

        AddToCartInputDTO input = new AddToCartInputDTO(CUSTOMER_ID, "prod-1", "Laptop", 2, new BigDecimal("3000.00"));

        // Act
        CartOutputDTO result = addToCartUseCase.execute(input);

        // Assert: 1 (existing) + 2 (new) = 3
        assertEquals(3, result.items().get(0).quantity());
        assertEquals(new BigDecimal("9000.00"), result.total());
    }

    @Test
    void shouldThrowWhenQuantityIsZeroOrNegative() {
        AddToCartInputDTO input = new AddToCartInputDTO(CUSTOMER_ID, "prod-1", "Laptop", 0, new BigDecimal("3000.00"));
        when(cartRepository.findByCustomerId(CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(com.lucasgrf.orderservice.domain.exception.DomainException.class,
                () -> addToCartUseCase.execute(input));
    }
}
