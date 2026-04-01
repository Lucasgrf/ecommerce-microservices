package com.lucasgrf.orderservice.application.usecase;

import com.lucasgrf.orderservice.domain.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClearCartUseCaseTest {

    @Mock private CartRepository cartRepository;
    @InjectMocks private ClearCartUseCase clearCartUseCase;

    @Test
    void shouldDelegateDeleteToRepository() {
        clearCartUseCase.execute("user-1");
        verify(cartRepository, times(1)).deleteByCustomerId("user-1");
    }
}
