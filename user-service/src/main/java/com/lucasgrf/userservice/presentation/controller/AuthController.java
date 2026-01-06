package com.lucasgrf.userservice.presentation.controller;

import com.lucasgrf.userservice.application.dto.LoginDTO;
import com.lucasgrf.userservice.application.dto.TokenResponseDTO;
import com.lucasgrf.userservice.application.usecase.LoginUseCase;
import com.lucasgrf.userservice.application.dto.UserRegistrationDTO;
import com.lucasgrf.userservice.application.dto.UserResponseDTO;
import com.lucasgrf.userservice.application.usecase.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with email and password")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegistrationDTO dto) {
        UserResponseDTO response = registerUserUseCase.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Validates credentials and returns a JWT token")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        TokenResponseDTO response = loginUseCase.execute(dto);
        return ResponseEntity.ok(response);
    }
}
