package com.lucasgrf.userservice.application.usecase;

import com.lucasgrf.userservice.application.dto.LoginDTO;
import com.lucasgrf.userservice.application.dto.TokenResponseDTO;
import com.lucasgrf.userservice.domain.model.User;
import com.lucasgrf.userservice.domain.repository.UserRepository;
import com.lucasgrf.userservice.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private LoginUseCase loginUseCase;

    @Test
    void shouldLoginSuccessfully() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        String token = "jwt.token.here";

        User user = User.builder()
                .id(UUID.randomUUID())
                .email(email)
                .password(encodedPassword)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtTokenProvider.generateToken(anyString(), any())).thenReturn(token);

        // Act
        TokenResponseDTO response = loginUseCase.execute(new LoginDTO(email, password));

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(token, response.token());
        Assertions.assertEquals("Bearer", response.type());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> 
            loginUseCase.execute(new LoginDTO("wrong@email.com", "pass"))
        );
    }

    @Test
    void shouldThrowExceptionWhenPasswordDoesNotMatch() {
        User user = User.builder().email("test@example.com").password("encoded").build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        Assertions.assertThrows(IllegalArgumentException.class, () -> 
            loginUseCase.execute(new LoginDTO("test@example.com", "wrongpass"))
        );
    }
}
