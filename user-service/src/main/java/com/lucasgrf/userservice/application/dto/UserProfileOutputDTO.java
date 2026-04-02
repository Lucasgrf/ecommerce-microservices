package com.lucasgrf.userservice.application.dto;

import lombok.Builder;

@Builder
public record UserProfileOutputDTO(
        String id,
        String email,
        String name,
        String role
) {}
