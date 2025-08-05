package com.emirkoral.deliveryapp.chatmessage;

import com.emirkoral.deliveryapp.chatmessage.dto.ChatMessageRequest;
import com.emirkoral.deliveryapp.chatmessage.dto.ChatMessageResponse;
import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;
import com.emirkoral.deliveryapp.exception.UnAuthorizedException;
import com.emirkoral.deliveryapp.order.Order;
import com.emirkoral.deliveryapp.order.OrderRepository;
import com.emirkoral.deliveryapp.user.User;
import com.emirkoral.deliveryapp.user.UserRepository;
import com.emirkoral.deliveryapp.util.AuthorizationUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository, ChatMessageMapper chatMessageMapper, OrderRepository orderRepository, UserRepository userRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatMessageMapper = chatMessageMapper;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public ChatMessageResponse saveMessage(ChatMessageRequest request) {
        User sender = userRepository.findById(request.senderId())
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found with id: " + request.senderId()));

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(sender.getEmail())) {
            throw new UnAuthorizedException("Sender ID must match the authenticated user.");
        }

        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + request.orderId()));

        checkOrderAuthorization(order);

        ChatMessage chatMessage = chatMessageMapper.toEntity(request);
        chatMessage.setTimestamp(LocalDateTime.now());
        ChatMessage saved = chatMessageRepository.save(chatMessage);
        return chatMessageMapper.toResponse(saved);
    }

    public List<ChatMessageResponse> getMessagesByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        checkOrderAuthorization(order);

        return chatMessageRepository.findByOrderIdOrderByTimestampAsc(orderId)
                .stream()
                .map(chatMessageMapper::toResponse)
                .toList();
    }

    private void checkOrderAuthorization(Order order) {
        String customerEmail = order.getCustomer() != null ? order.getCustomer().getEmail() : null;
        String restaurantOwnerEmail = order.getRestaurant() != null && order.getRestaurant().getOwner() != null ?
                order.getRestaurant().getOwner().getEmail() : null;
        String driverEmail = order.getDelivery() != null && order.getDelivery().getDriver() != null ?
                order.getDelivery().getDriver().getEmail() : null;

        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN),
                customerEmail, restaurantOwnerEmail, driverEmail);
    }
} 