package com.lucasgrf.userservice.presentation.controller;

import com.lucasgrf.userservice.application.dto.AuthResponseDTO;
import com.lucasgrf.userservice.application.dto.LoginInputDTO;
import com.lucasgrf.userservice.application.usecase.AuthenticateUserUseCase;
import com.lucasgrf.userservice.presentation.dto.LoginRequest;

import com.lucasgrf.userservice.application.dto.RegisterUserInputDTO;
import com.lucasgrf.userservice.application.dto.UserOutputDTO;
import com.lucasgrf.userservice.application.usecase.RegisterUserUseCase;
import com.lucasgrf.userservice.presentation.dto.RegisterRequest;
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
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    @PostMapping("/register")
    public ResponseEntity<UserOutputDTO> register(@Valid @RequestBody RegisterRequest request) {
        RegisterUserInputDTO input = new RegisterUserInputDTO(
                request.name(),
                request.email(),
                request.password());
        UserOutputDTO output = registerUserUseCase.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequest request) {
        LoginInputDTO input = new LoginInputDTO(request.email(), request.password());
        AuthResponseDTO response = authenticateUserUseCase.execute(input);
        return ResponseEntity.ok(response);
    }
}
