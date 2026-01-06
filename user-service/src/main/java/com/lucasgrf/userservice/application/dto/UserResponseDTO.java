package com.lucasgrf.userservice.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String email,
        LocalDateTime createdAt
) {}
