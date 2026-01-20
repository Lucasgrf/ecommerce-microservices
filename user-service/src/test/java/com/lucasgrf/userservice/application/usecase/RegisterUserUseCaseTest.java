package com.lucasgrf.userservice.application.usecase;

import com.lucasgrf.userservice.application.dto.RegisterUserInputDTO;
import com.lucasgrf.userservice.application.dto.UserOutputDTO;
import com.lucasgrf.userservice.domain.entity.User;
import com.lucasgrf.userservice.domain.exception.EmailAlreadyExistsException;
import com.lucasgrf.userservice.domain.repository.UserRepository;
import com.lucasgrf.userservice.domain.valueobject.Email;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        RegisterUserInputDTO input = new RegisterUserInputDTO("Lucas", "lucas@test.com", "123456");
        when(userRepository.existsByEmail(any(Email.class))).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("hashed_password");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            return user;
        });

        // Act
        UserOutputDTO output = registerUserUseCase.execute(input);

        // Assert
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals("Lucas", output.name());
        Assertions.assertEquals("lucas@test.com", output.email());
        Assertions.assertEquals("CUSTOMER", output.role());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        // Arrange
        RegisterUserInputDTO input = new RegisterUserInputDTO("Lucas", "lucas@test.com", "123456");
        when(userRepository.existsByEmail(any(Email.class))).thenReturn(true);

        // Act & Assert
        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> {
            registerUserUseCase.execute(input);
        });
        verify(userRepository, never()).save(any(User.class));
    }
}
