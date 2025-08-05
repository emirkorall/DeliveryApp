package com.emirkoral.deliveryapp.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record ReviewRequest(
        @NotNull(message = "Rating is required")
        @Min(value = 1, message = "Minimum rating is 1")
        @Max(value = 5, message = "Maximum rating is 5")
        Double rating,
        String comment,
        @NotNull(message = "Customer ID is required")
        Long customerId,
        @NotNull(message = "Restaurant ID is required")
        Long restaurantId,
        @NotNull(message = "Order ID is required")
        Long orderId


) {
}
