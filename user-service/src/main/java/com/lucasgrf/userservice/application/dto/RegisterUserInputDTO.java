package com.lucasgrf.userservice.application.dto;

public record RegisterUserInputDTO(
        String name,
        String email,
        String password) {
}
