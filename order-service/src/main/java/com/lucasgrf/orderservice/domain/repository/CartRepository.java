package com.lucasgrf.orderservice.domain.repository;

import com.lucasgrf.orderservice.domain.entity.Cart;

import java.util.Optional;

/**
 * Domain port for cart persistence.
 * The infrastructure adapter (RedisCartRepository) implements this.
 */
public interface CartRepository {
    Optional<Cart> findByCustomerId(String customerId);
    Cart save(Cart cart);
    void deleteByCustomerId(String customerId);
}
