package com.lucasgrf.userservice.domain.repository;

import com.lucasgrf.userservice.domain.model.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
