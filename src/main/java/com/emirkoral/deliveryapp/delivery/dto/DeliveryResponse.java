package com.emirkoral.deliveryapp.delivery.dto;

import com.emirkoral.deliveryapp.delivery.Delivery;

import java.time.LocalDateTime;

public record DeliveryResponse(Long id,
                               LocalDateTime pickupTime,
                               LocalDateTime deliveryTime,
                               Integer estimatedDuration,
                               Integer actualDuration,
                               Delivery.Status status,
                               Double currentLatitude,
                               Double currentLongitude,
                               Long orderId,
                               Long driverId,
                               Long deliveryAddressId) {
}
