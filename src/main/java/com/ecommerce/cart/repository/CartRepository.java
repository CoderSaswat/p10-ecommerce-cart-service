package com.ecommerce.cart.repository;

import com.ecommerce.cart.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends MongoRepository<Cart, UUID> {
    Optional<Cart> findByUserId(UUID userId);
}
