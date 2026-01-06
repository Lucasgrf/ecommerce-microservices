package com.lucasgrf.userservice.application.usecase;

import com.lucasgrf.userservice.application.dto.UserRegistrationDTO;
import com.lucasgrf.userservice.application.dto.UserResponseDTO;
import com.lucasgrf.userservice.domain.model.User;
import com.lucasgrf.userservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO execute(UserRegistrationDTO dto) {
        log.info("Attempting to register user with email: {}", dto.email());

        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        String encodedPassword = passwordEncoder.encode(dto.password());

        User newUser = User.builder()
                .email(dto.email())
                .password(encodedPassword)
                .build();

        User savedUser = userRepository.save(newUser);
        log.info("User registered successfully with ID: {}", savedUser.getId());

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getCreatedAt()
        );
    }
}
