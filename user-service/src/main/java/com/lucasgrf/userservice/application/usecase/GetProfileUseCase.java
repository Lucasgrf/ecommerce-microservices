package com.lucasgrf.userservice.application.usecase;

import com.lucasgrf.userservice.application.dto.UserProfileOutputDTO;
import com.lucasgrf.userservice.domain.entity.User;
import com.lucasgrf.userservice.domain.exception.UserNotFoundException;
import com.lucasgrf.userservice.domain.repository.UserRepository;
import com.lucasgrf.userservice.domain.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProfileUseCase {

    private final UserRepository userRepository;

    public UserProfileOutputDTO execute(String userId) {
        User user = userRepository.findById(new UserId(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return UserProfileOutputDTO.builder()
                .id(user.getId().value())
                .email(user.getEmail().value())
                .name(user.getName())
                .role(user.getRole().name())
                .build();
    }
}
