package com.ecommerce.cart.controller;

import com.ecommerce.cart.dto.CartDto;
import com.ecommerce.cart.dto.CartItemDto;
import com.ecommerce.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public CartDto getCart(@PathVariable UUID userId) {
        return cartService.getCart(userId);
    }

    @PostMapping("/{userId}/addItem")
    public CartDto addItemToCart(@PathVariable UUID userId, @RequestBody CartItemDto cartItemDto) {
        return cartService.addItemToCart(userId, cartItemDto);
    }

    @PutMapping("/{userId}/updateItemQuantity")
    public CartDto updateItemQuantity(@PathVariable UUID userId, @RequestBody CartItemDto cartItemDto) {
        return cartService.updateItemQuantity(userId, cartItemDto);
    }

    @DeleteMapping("/{userId}/removeItem/{productId}")
    public void removeItemFromCart(@PathVariable UUID userId, @PathVariable UUID productId) {
        cartService.removeItemFromCart(userId, productId);
    }

    @GetMapping("/all")
    public List<CartDto> getAllCarts() {
        return cartService.getAllCarts();
    }

}
