package com.lucasgrf.userservice.application.dto;

import com.lucasgrf.userservice.domain.entity.User;

public record UserOutputDTO(
        String id,
        String name,
        String email,
        String role) {
    public static UserOutputDTO from(User user) {
        return new UserOutputDTO(
                user.getId().value(),
                user.getName(),
                user.getEmail().value(),
                user.getRole().name());
    }
}
