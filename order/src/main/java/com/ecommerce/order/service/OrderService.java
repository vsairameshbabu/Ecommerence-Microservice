package com.ecommerce.order.service;


import com.ecommerce.order.dto.OrderItemDTO;
import com.ecommerce.order.dto.OrderResponse;
//import com.ecommerce.order.models.User;
import com.ecommerce.order.repository.OrderRepository;
//import com.ecommerce.order.repository.UserRepository;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.models.Order;
import com.ecommerce.order.models.OrderItem;
import com.ecommerce.order.models.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartItemService cartItemService;
//    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(Long userId) {
        //cartItem validate
        List<CartItem> cartItem = cartItemService.getCart(String.valueOf(userId));
        if (cartItem.isEmpty()) {
            return Optional.empty();
        }

//        //user Validate
//        Optional<User> userOp = userRepository.findById(Long.parseLong(userId));
//        if (userOp.isEmpty()) {
//            return Optional.empty();
//        }
//        User user = userOp.get();

        //calculate total
        BigDecimal totalAmount = cartItem.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // create order
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.CONFIRMED);
        List<OrderItem> orderItems = cartItem.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order)).toList();
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // clear cart
        cartItemService.clearCart(String.valueOf(userId));

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getId().toString(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus().name(),
                savedOrder.getOrderItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId().toString(),
                                orderItem.getProductId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))))
                        .toList(),
                savedOrder.getCreatedAt().toString());
    }
}
