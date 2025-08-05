package com.emirkoral.deliveryapp.courierlocation.dto;

import java.time.LocalDateTime;

public record CourierLocationResponse(
    Long id,
    Long courierId,
    Long orderId,
    Double latitude,
    Double longitude,
    LocalDateTime timestamp
) {} 