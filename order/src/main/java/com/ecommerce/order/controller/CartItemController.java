package com.ecommerce.order.controller;

import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-Id") String userId,
            @RequestBody CartItemRequest cartItemRequest) {
        if (!cartItemService.addToCart(userId, cartItemRequest)) {
            return ResponseEntity.badRequest().body("Product not found or out of stock or invalid UserId");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Added Product To Cart Sucessfully");
    }

    @DeleteMapping("items/{productId}")
    public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-Id") Long userId,
                                               @PathVariable Long productId) {
        boolean deleted = cartItemService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();

    }

    @GetMapping()
    public ResponseEntity<List<CartItem>> getCart(@RequestHeader("X-User-Id") Long userId) {
        return new ResponseEntity<>(cartItemService.getCart(String.valueOf(userId)), HttpStatus.OK);
    }
}
