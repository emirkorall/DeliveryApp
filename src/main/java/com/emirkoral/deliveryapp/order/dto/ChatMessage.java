package com.emirkoral.deliveryapp.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record ChatMessage(
    @NotNull Long orderId,
    @NotNull Long senderId,
    @NotBlank String senderRole,
    @NotBlank String message,
    @NotBlank String timestamp
) {}
