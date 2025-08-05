package com.emirkoral.deliveryapp.restaurant.dto;

import java.math.BigDecimal;

public record RestaurantResponse(Long id,
                                 String name,
                                 String description,
                                 String address,
                                 Double latitude,
                                 Double longitude,
                                 String phone,
                                 String email,
                                 String cuisine,
                                 Double rating,
                                 Boolean isActive,
                                 String openingHours,
                                 Double deliveryRadius,
                                 BigDecimal minimumOrder,
                                 BigDecimal deliveryFee,
                                 Long ownerId) {
}
