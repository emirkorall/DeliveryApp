package com.emirkoral.deliveryapp.order.dto;

import jakarta.validation.constraints.NotNull;

public record CourierLocationRequest(
    @NotNull Double latitude,
    @NotNull Double longitude,
    @NotNull Long orderId,
    @NotNull Long courierId
) {} 