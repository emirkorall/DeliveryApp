package com.emirkoral.deliveryapp.delivery.dto;

import jakarta.validation.constraints.NotNull;

public record DeliveryRequest(
        @NotNull(message = "Order ID is required")
        Long orderId,
        @NotNull(message = "Driver ID is required")
        Long driverId,
        @NotNull(message = "Delivery address ID is required")
        Long deliveryAddressId
) {
}
