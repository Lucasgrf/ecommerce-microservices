package com.lucasgrf.userservice.presentation.controller;

import com.lucasgrf.userservice.application.dto.UpdateProfileInputDTO;
import com.lucasgrf.userservice.application.dto.UserProfileOutputDTO;
import com.lucasgrf.userservice.application.usecase.GetProfileUseCase;
import com.lucasgrf.userservice.application.usecase.UpdateProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Profile", description = "Endpoints for managing user profile")
public class UserController {

    private final GetProfileUseCase getProfileUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;

    private String getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/me")
    @Operation(summary = "Get user profile", description = "Retrieves the profile of the currently authenticated user", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<UserProfileOutputDTO> getProfile() {
        return ResponseEntity.ok(getProfileUseCase.execute(getAuthenticatedUserId()));
    }

    @PutMapping("/me")
    @Operation(summary = "Update user profile", description = "Updates the profile of the currently authenticated user", security = @SecurityRequirement(name = "Bearer Authentication"))
    public ResponseEntity<UserProfileOutputDTO> updateProfile(@Valid @RequestBody UpdateProfileInputDTO input) {
        return ResponseEntity.ok(updateProfileUseCase.execute(getAuthenticatedUserId(), input));
    }
}
