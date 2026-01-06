package com.lucasgrf.userservice.application.dto;

public record TokenResponseDTO(
        String token,
        String type,
        long expiresIn
) {
    public TokenResponseDTO(String token, long expiresIn) {
        this(token, "Bearer", expiresIn);
    }
}
