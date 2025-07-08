package com.emirkoral.deliveryapp.menuitem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MenuItemRequest(
        @NotBlank(message = "Name is required")
        String name,
        String description,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,
        String category,
        String imageUrl,
        @NotNull(message = "Availability is required")
        Boolean isAvailable,
        Integer preparationTime,
        @NotNull(message = "Restaurant ID is required")
        Long restaurantId


        ) {
}
