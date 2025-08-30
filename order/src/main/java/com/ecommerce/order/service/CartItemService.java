package com.ecommerce.order.service;

import com.ecommerce.order.dto.CartItemRequest;
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

//    private final ProductRepository productRepository;
//    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
//        Optional<Product> productOp = productRepository.findById(cartItemRequest.getProductId());
//        if(productOp.isEmpty()){
//            return false;
//        }
//        Product product = productOp.get();
//        if(product.getStockQuantity() < cartItemRequest.getQuantity()){
//            return false;
//        }
//        Optional<User> userOp = userRepository.findById(Long.parseLong(userId));
//        if(userOp.isEmpty()){
//            return false;
//        }
//        User user = userOp.get();

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
//        Optional<Product> productOp = productRepository.findById(productId);
//        Optional<User> userOp = userRepository.findById(Long.parseLong(userId));
        CartItem cartItem = cartItemRepository.findByProductIdAndUserId(productId, Long.valueOf(userId));
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
