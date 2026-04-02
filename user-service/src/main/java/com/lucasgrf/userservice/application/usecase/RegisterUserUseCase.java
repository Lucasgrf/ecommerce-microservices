package com.lucasgrf.userservice.application.usecase;

import com.lucasgrf.userservice.application.dto.RegisterUserInputDTO;
import com.lucasgrf.userservice.application.dto.UserOutputDTO;
import com.lucasgrf.userservice.domain.entity.User;
import com.lucasgrf.userservice.domain.entity.UserRole;
import com.lucasgrf.userservice.domain.exception.EmailAlreadyExistsException;
import com.lucasgrf.userservice.domain.repository.UserRepository;
import com.lucasgrf.userservice.domain.valueobject.Email;
import com.lucasgrf.userservice.domain.valueobject.Password;
import com.lucasgrf.userservice.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserOutputDTO execute(RegisterUserInputDTO input) {
        // 1. Validar se email já existe
        Email email = new Email(input.email());
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(input.email());
        }

        // 2. Criar entidade de domínio
        User user = User.builder()
                .id(UserId.generate())
                .email(email)
                .password(new Password(passwordEncoder.encode(input.password())))
                .name(input.name())
                .role(UserRole.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .active(true)
                .build();

        // 3. Persistir
        User saved = userRepository.save(user);

        // 4. Retornar DTO
        return UserOutputDTO.from(saved);
    }
}
