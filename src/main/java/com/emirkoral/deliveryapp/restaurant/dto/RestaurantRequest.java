package com.emirkoral.deliveryapp.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.aspectj.weaver.ast.Not;

import java.math.BigDecimal;

public record RestaurantRequest(
        @NotBlank(message = "Name is required")
        String name,
        @Size(max = 500, message = "Description too long")
        String description,
        @NotBlank(message = "Address is required")
        String address,
        @NotNull(message = "Latitude is required")
        Double latitude,
        @NotNull(message = "Longitude is required")
        Double longitude,
        @NotBlank(message = "Phone is required")
        String phone,
        @NotBlank(message = "Email is required")
        String cuisine,
        String openingHours,
        @NotNull(message = "Delivery radius is required")
        Double deliveryRadius,
        @NotNull(message = "Minimum order is required")
        BigDecimal minimumOrder,
        @NotNull(message = "Delivery fee is required")
        BigDecimal deliveryFee,
        @NotNull(message = "Owner ID is required")
        Long ownerId

) {
}
