package com.lucasgrf.userservice.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class SecurityProperties {
    private String secretKey = "mySecretKeyMySecretKeyMySecretKeyMySecretKey";
    private long expirationTime = 86400000; // 1 day
}
