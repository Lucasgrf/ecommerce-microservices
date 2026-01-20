package com.lucasgrf.userservice.application.usecase;

import com.lucasgrf.userservice.application.dto.AuthResponseDTO;
import com.lucasgrf.userservice.application.dto.LoginInputDTO;
import com.lucasgrf.userservice.domain.entity.User;
import com.lucasgrf.userservice.domain.entity.UserRole;
import com.lucasgrf.userservice.domain.exception.InvalidCredentialsException;
import com.lucasgrf.userservice.domain.repository.UserRepository;
import com.lucasgrf.userservice.domain.valueobject.Email;
import com.lucasgrf.userservice.domain.valueobject.Password;
import com.lucasgrf.userservice.domain.valueobject.UserId;
import com.lucasgrf.userservice.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthenticateUserUseCase authenticateUserUseCase;

    @Test
    void shouldAuthenticateSuccessfully() {
        // Arrange
        LoginInputDTO input = new LoginInputDTO("lucas@test.com", "123456");
        User user = User.builder()
                .id(UserId.generate())
                .email(new Email("lucas@test.com"))
                .password(new Password("hashed_password"))
                .name("Lucas")
                .role(UserRole.CUSTOMER)
                .active(true)
                .build();

        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "hashed_password")).thenReturn(true);
        when(tokenProvider.generateToken(user)).thenReturn("valid_token");

        // Act
        AuthResponseDTO response = authenticateUserUseCase.execute(input);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals("valid_token", response.token());
        Assertions.assertEquals("Lucas", response.name());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        // Arrange
        LoginInputDTO input = new LoginInputDTO("lucas@test.com", "wrong_password");
        User user = User.builder()
                .email(new Email("lucas@test.com"))
                .password(new Password("hashed_password"))
                .build();

        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong_password", "hashed_password")).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(InvalidCredentialsException.class, () -> {
            authenticateUserUseCase.execute(input);
        });
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        LoginInputDTO input = new LoginInputDTO("nonexistent@test.com", "123456");
        when(userRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(InvalidCredentialsException.class, () -> {
            authenticateUserUseCase.execute(input);
        });
    }
}
