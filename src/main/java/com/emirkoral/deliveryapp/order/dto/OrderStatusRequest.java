package com.emirkoral.deliveryapp.order.dto;

import jakarta.validation.constraints.NotBlank;

public record OrderStatusRequest(
    @NotBlank String status,
    @NotBlank String message
) {} 