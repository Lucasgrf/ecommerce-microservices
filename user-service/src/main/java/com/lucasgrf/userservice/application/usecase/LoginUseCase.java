package com.lucasgrf.userservice.application.usecase;

import com.lucasgrf.userservice.application.dto.LoginDTO;
import com.lucasgrf.userservice.application.dto.TokenResponseDTO;
import com.lucasgrf.userservice.domain.model.User;
import com.lucasgrf.userservice.domain.repository.UserRepository;
import com.lucasgrf.userservice.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponseDTO execute(LoginDTO dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRoles());
        // Default 1h (3600000 ms) - hardcoded here or getting from provider if exposed
        // Ideally we grab it from provider or config. For now using same logic as provider default
        return new TokenResponseDTO(token, 3600000); // 1 hour
    }
}
