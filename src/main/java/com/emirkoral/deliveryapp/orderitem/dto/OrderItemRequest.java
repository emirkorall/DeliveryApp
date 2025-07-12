package com.emirkoral.deliveryapp.orderitem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(
        @NotNull(message = "Order Id is required")
        Long orderId,
        @NotNull(message = "Menu item ID is required")
        Long menuItemId,
        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        Integer quantity,
        String specialInstructions
) {
}
