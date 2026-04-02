package com.lucasgrf.userservice.application.usecase;

import com.lucasgrf.userservice.application.dto.AuthResponseDTO;
import com.lucasgrf.userservice.application.dto.LoginInputDTO;
import com.lucasgrf.userservice.domain.entity.User;
import com.lucasgrf.userservice.domain.exception.InvalidCredentialsException;
import com.lucasgrf.userservice.domain.repository.UserRepository;
import com.lucasgrf.userservice.domain.valueobject.Email;
import com.lucasgrf.userservice.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthResponseDTO execute(LoginInputDTO input) {
        // 1. Validar email format
        Email email = new Email(input.email());

        // 2. Buscar usuário
        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        // 3. Validar senha
        if (!passwordEncoder.matches(input.password(), user.getPassword().hashedValue())) {
            throw new InvalidCredentialsException();
        }

        // 4. Gerar Token
        String token = tokenProvider.generateToken(user);

        return new AuthResponseDTO(token, user.getName(), user.getRole().name());
    }
}
