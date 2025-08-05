package com.emirkoral.deliveryapp.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record AssignmentNotification(
    @NotNull Long orderId,
    @NotNull Long courierId,
    @NotBlank String status,
    @NotBlank String message
) {} 