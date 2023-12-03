package com.ecommerce.cart.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Document("cart)")
public class CartItem {
    @Id
    private UUID id;
    private UUID productId;
    private int quantity;
}
