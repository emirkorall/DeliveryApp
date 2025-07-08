package com.emirkoral.deliveryapp.review.dto;

import java.time.LocalDateTime;

public record ReviewResponse(Long id,
                             Integer rating,
                             String comment,
                             LocalDateTime createdAt,
                             Long customerId,
                             Long restaurantId,
                             Long orderId) {
}
