package com.ecommerce.order.service;

import com.ecommerce.order.clients.ProductServiceClient;
import com.ecommerce.order.clients.UserServiceClient;
import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.dto.ProductResponse;
import com.ecommerce.order.dto.UserResponse;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartItemService {

    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        //fetch product details from product service
        ProductResponse productResponse = productServiceClient.getProduct(String.valueOf(cartItemRequest.getProductId()));
        if(productResponse == null || productResponse.getStockQuantity() < cartItemRequest.getQuantity()){
            return false;
        }

        // fetch user details from user service
        UserResponse userResponse = userServiceClient.getUser(userId);
        if(userResponse == null){
            return false;
        }

        CartItem existingCartItem = cartItemRepository.findByProductIdAndUserId(cartItemRequest.getProductId(), Long.valueOf(userId));
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
//            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            existingCartItem.setPrice(BigDecimal.valueOf(1000));
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setUserId(Long.valueOf(userId));
            cartItem.setQuantity(cartItemRequest.getQuantity());
//            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItem.setPrice(BigDecimal.valueOf(1000));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(Long userId, Long productId) {
        ProductResponse productOp = productServiceClient.getProduct(String.valueOf(productId));
        UserResponse userOp = userServiceClient.getUser(String.valueOf(userId));
        CartItem cartItem = cartItemRepository.findByProductIdAndUserId(productId, userId);
        if(cartItem != null) {
            cartItemRepository.deleteByUserIdAndProductId(userId, productId);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
//       return  userRepository.findById(Long.parseLong(userId))
//                .map(cartItemRepository::findByUserId)
//                .orElseGet(List::of);
        return cartItemRepository.findByUserId(Long.valueOf(userId));
    }

    public void clearCart(String userId) {
//        userRepository.findById(Long.parseLong(userId))
//                .ifPresent(cartItemRepository::deleteByUserId);
        cartItemRepository.deleteByUserId(Long.valueOf(userId));
    }
}
