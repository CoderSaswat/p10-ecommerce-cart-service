package com.ecommerce.cart.service;


import com.ecommerce.cart.dto.CartDto;
import com.ecommerce.cart.dto.CartItemDto;

import java.util.List;
import java.util.UUID;

public interface CartService {
    CartDto getCart(UUID userId);
    CartDto addItemToCart(UUID userId, CartItemDto cartItemDto);
    CartDto updateItemQuantity(UUID userId, CartItemDto cartItemDto);
    void removeItemFromCart(UUID userId, UUID productId);
    List<CartDto> getAllCarts();
}
