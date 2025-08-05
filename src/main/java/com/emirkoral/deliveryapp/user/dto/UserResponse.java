package com.emirkoral.deliveryapp.user.dto;

import com.emirkoral.deliveryapp.user.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone,
        User.UserRole role,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
