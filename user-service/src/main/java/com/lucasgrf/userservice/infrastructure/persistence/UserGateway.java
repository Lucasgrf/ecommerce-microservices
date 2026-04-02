package com.lucasgrf.userservice.infrastructure.persistence;

import com.lucasgrf.userservice.domain.entity.User;
import com.lucasgrf.userservice.domain.repository.UserRepository;
import com.lucasgrf.userservice.domain.valueobject.Email;
import com.lucasgrf.userservice.domain.valueobject.Password;
import com.lucasgrf.userservice.domain.valueobject.UserId;
import com.lucasgrf.userservice.infrastructure.persistence.entity.UserEntity;
import com.lucasgrf.userservice.infrastructure.persistence.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserGateway implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> findById(UserId id) {
        return jpaUserRepository.findById(id.value())
                .map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaUserRepository.findByEmail(email.value())
                .map(this::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saved = jpaUserRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return jpaUserRepository.existsByEmail(email.value());
    }

    private User toDomain(UserEntity entity) {
        return User.builder()
                .id(new UserId(entity.getId()))
                .email(new Email(entity.getEmail()))
                .password(new Password(entity.getPassword()))
                .name(entity.getName())
                .role(entity.getRole())
                .createdAt(entity.getCreatedAt())
                .active(entity.isActive())
                .build();
    }

    private UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId().value())
                .email(user.getEmail().value())
                .password(user.getPassword().hashedValue())
                .name(user.getName())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .active(user.isActive())
                .build();
    }
}
