package com.lucasgrf.orderservice.infrastructure.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasgrf.orderservice.domain.entity.Cart;
import com.lucasgrf.orderservice.domain.exception.DomainException;
import com.lucasgrf.orderservice.domain.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Redis adapter implementing the CartRepository domain port.
 *
 * Key format:  cart:{customerId}
 * Value format: JSON string (CartRedisDTO)
 * TTL: configurable via cart.ttl-days (default 7 days)
 */
@Repository
@RequiredArgsConstructor
public class RedisCartRepository implements CartRepository {

    private static final String KEY_PREFIX = "cart:";

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${cart.ttl-days:7}")
    private long ttlDays;

    @Override
    public Optional<Cart> findByCustomerId(String customerId) {
        String json = redisTemplate.opsForValue().get(key(customerId));
        if (json == null) {
            return Optional.empty();
        }
        try {
            CartRedisDTO dto = objectMapper.readValue(json, CartRedisDTO.class);
            return Optional.of(dto.toDomain());
        } catch (JsonProcessingException e) {
            throw new DomainException("Failed to deserialize cart for customer: " + customerId);
        }
    }

    @Override
    public Cart save(Cart cart) {
        try {
            CartRedisDTO dto = CartRedisDTO.from(cart);
            String json = objectMapper.writeValueAsString(dto);
            redisTemplate.opsForValue().set(key(cart.getCustomerId()), json, ttlDays, TimeUnit.DAYS);
            return cart;
        } catch (JsonProcessingException e) {
            throw new DomainException("Failed to serialize cart for customer: " + cart.getCustomerId());
        }
    }

    @Override
    public void deleteByCustomerId(String customerId) {
        redisTemplate.delete(key(customerId));
    }

    private String key(String customerId) {
        return KEY_PREFIX + customerId;
    }
}
