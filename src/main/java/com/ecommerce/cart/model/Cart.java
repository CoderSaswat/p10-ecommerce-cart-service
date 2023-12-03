package com.ecommerce.cart.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document("cart")
public class Cart {
    @Id
    private UUID id;
    private UUID userId;
    private List<CartItem> cartItems = new ArrayList<>();
}
