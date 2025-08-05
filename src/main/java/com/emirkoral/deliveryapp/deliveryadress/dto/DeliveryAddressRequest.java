package com.emirkoral.deliveryapp.deliveryadress.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeliveryAddressRequest(
        @NotBlank(message = "Address is required")
        String address,
        @NotNull(message = "Latitude is required")
        Double latitude,
        @NotNull(message = "Longitude is required")
        Double longitude,
        String apartment,
        String floor,
        String building,
        String instructions
) {
}
