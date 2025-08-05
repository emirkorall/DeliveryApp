package com.emirkoral.deliveryapp.chatmessage.dto;

import com.emirkoral.deliveryapp.user.User.UserRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record ChatMessageRequest(
    @NotNull Long orderId,
    @NotNull Long senderId,
    @NotNull UserRole senderRole,
    @NotBlank String message
) {} 