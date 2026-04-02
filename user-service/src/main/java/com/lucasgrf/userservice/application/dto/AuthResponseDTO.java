package com.lucasgrf.userservice.application.dto;

public record AuthResponseDTO(
        String token,
        String name,
        String role) {
}
