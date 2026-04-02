package com.lucasgrf.userservice.domain.repository;

import com.lucasgrf.userservice.domain.entity.User;
import com.lucasgrf.userservice.domain.valueobject.Email;
import com.lucasgrf.userservice.domain.valueobject.UserId;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(UserId id);

    Optional<User> findByEmail(Email email);

    User save(User user);

    boolean existsByEmail(Email email);
}
