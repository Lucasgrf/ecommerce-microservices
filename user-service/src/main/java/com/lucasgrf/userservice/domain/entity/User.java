package com.lucasgrf.userservice.domain.entity;

import com.lucasgrf.userservice.domain.valueobject.Email;
import com.lucasgrf.userservice.domain.valueobject.Password;
import com.lucasgrf.userservice.domain.valueobject.UserId;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class User {
    private final UserId id;
    private final Email email;
    private final Password password;
    private final String name;
    private final UserRole role;
    private final LocalDateTime createdAt;
    private boolean active;

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
