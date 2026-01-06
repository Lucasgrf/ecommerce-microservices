package com.lucasgrf.userservice.infrastructure.persistence;

import com.lucasgrf.userservice.domain.model.User;
import com.lucasgrf.userservice.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<User, UUID>, UserRepository {
    // Methods defined in UserRepository are automatically implemented by Spring Data JPA
    // if they follow the naming convention.
}
