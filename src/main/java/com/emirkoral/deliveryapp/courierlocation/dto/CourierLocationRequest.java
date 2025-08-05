package com.emirkoral.deliveryapp.courierlocation.dto;

import jakarta.validation.constraints.NotNull;

public record CourierLocationRequest(
    @NotNull Double latitude,
    @NotNull Double longitude,
    @NotNull Long orderId,
    @NotNull Long courierId
) {} 