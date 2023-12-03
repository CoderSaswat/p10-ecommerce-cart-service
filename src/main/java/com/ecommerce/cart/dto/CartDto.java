package com.ecommerce.cart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDto {
    private UUID id;
    private UUID userId;
    private List<CartItemDto> cartItems;
    private Double totalAmount;
}
