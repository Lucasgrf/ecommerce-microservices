package com.lucasgrf.userservice.application.usecase;

import com.lucasgrf.userservice.application.dto.UserRegistrationDTO;
import com.lucasgrf.userservice.application.dto.UserResponseDTO;
import com.lucasgrf.userservice.domain.model.User;
import com.lucasgrf.userservice.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;

    @Test
    void shouldRegisterUserSuccessfully() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO("test@example.com", "password123");
        String encodedPassword = "encodedPassword";
        
        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(passwordEncoder.encode(dto.password())).thenReturn(encodedPassword);
        
        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .email(dto.email())
                .password(encodedPassword)
                .createdAt(LocalDateTime.now())
                .build();
        
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponseDTO response = registerUserUseCase.execute(dto);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(savedUser.getId(), response.id());
        Assertions.assertEquals(dto.email(), response.email());
        
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        UserRegistrationDTO dto = new UserRegistrationDTO("test@example.com", "password123");
        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            registerUserUseCase.execute(dto);
        });

        verify(userRepository, Mockito.never()).save(any());
    }
}
