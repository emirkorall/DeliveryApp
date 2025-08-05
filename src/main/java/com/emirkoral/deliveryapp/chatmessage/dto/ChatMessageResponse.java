package com.emirkoral.deliveryapp.chatmessage.dto;

import com.emirkoral.deliveryapp.user.User.UserRole;
import java.time.LocalDateTime;

public record ChatMessageResponse(
    Long id,
    Long orderId,
    Long senderId,
    UserRole senderRole,
    String message,
    LocalDateTime timestamp
) {} 