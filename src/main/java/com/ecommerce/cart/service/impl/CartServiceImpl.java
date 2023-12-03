package com.ecommerce.cart.service.impl;

import com.ecommerce.cart.dto.CartDto;
import com.ecommerce.cart.dto.CartItemDto;
import com.ecommerce.cart.dto.ProductDto;
import com.ecommerce.cart.exception.BusinessException;
import com.ecommerce.cart.model.Cart;
import com.ecommerce.cart.model.CartItem;
import com.ecommerce.cart.repository.CartRepository;
import com.ecommerce.cart.service.CartService;
import com.ecommerce.cart.serviceclient.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;

    @Override
    public CartDto getCart(UUID userId) {
        Cart cart = getOrCreateCart(userId);
        List<UUID> productIds = cart.getCartItems().stream().map(CartItem::getProductId).toList();
        List<ProductDto> productDtoList = productServiceClient.getProductsByIds(productIds);
        Map<UUID, ProductDto> productDtoMap = productDtoList.stream()
                .collect(Collectors.toMap(ProductDto::getId, productDto -> productDto));
        CartDto cartDto = modelMapper.map(cart, CartDto.class);
        Double totalAmount= 0.0;
        for (CartItemDto cartItem : cartDto.getCartItems()) {
            ProductDto productDto = productDtoMap.get(cartItem.getProductId());
            cartItem.setAmount(productDto.getPrice()*cartItem.getQuantity());
            cartItem.setProductName(productDto.getName());
            totalAmount+=productDto.getPrice()*cartItem.getQuantity();
        }
        cartDto.setTotalAmount(totalAmount);
        return cartDto;
    }

    @Override
    public CartDto addItemToCart(UUID userId, CartItemDto cartItemDto) {
        Cart cart = getOrCreateCart(userId);
        List<CartItem> cartItemList = cart.getCartItems();
        CartItem cartItem = modelMapper.map(cartItemDto, CartItem.class);
        cartItem.setId(UUID.randomUUID());
        cartItemList.add(cartItem);
        cart.setCartItems(cartItemList);
        Cart updatedCart = cartRepository.save(cart);
        return modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public CartDto updateItemQuantity(UUID userId, CartItemDto cartItemDto) {
        Cart cart = getOrCreateCart(userId);

        // Find the item in the cart
        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(cartItemDto.getProductId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // Update the quantity of the existing item
            existingItem.setQuantity(cartItemDto.getQuantity());
        } else {
            throw new BusinessException("specific item not present in the cart");
        }

        Cart updatedCart = cartRepository.save(cart);
        return modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(UUID userId, UUID productId) {
        Cart cart = getOrCreateCart(userId);
        // Remove the item from the cart
        cart.getCartItems().removeIf(item -> item.getProductId().equals(productId));
        cartRepository.save(cart);
    }

    @Override
    public List<CartDto> getAllCarts() {
        List<Cart> allCarts = cartRepository.findAll();
        return allCarts.stream()
                .map(cart -> modelMapper.map(cart, CartDto.class))
                .collect(Collectors.toList());
    }

    // Helper method to get or create a cart for a user
    private Cart getOrCreateCart(UUID userId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if(optionalCart.isEmpty()){
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setId(UUID.randomUUID());
            return cart;
        }
        return optionalCart.get();
    }
}
