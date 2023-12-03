package com.ecommerce.cart.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class CartItemDto {
    private UUID id;
    private UUID productId;
    private Integer quantity;
    private String productName;
    private Double amount;
}
