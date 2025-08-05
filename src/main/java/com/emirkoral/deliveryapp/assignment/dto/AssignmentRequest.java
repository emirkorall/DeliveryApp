package com.emirkoral.deliveryapp.assignment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record AssignmentRequest(
    @NotNull Long orderId,
    @NotNull Long courierId,
    @NotBlank String status,
    @NotBlank String message
) {} 