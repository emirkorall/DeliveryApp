package com.emirkoral.deliveryapp.menuitem.dto;

public record MenuItemResponse(Long id,
                               String name,
                               String description,
                               Double price,
                               String category,
                               String imageUrl,
                               Boolean isAvailable,
                               Integer preparationTime,
                               Long restaurantId) {
}
