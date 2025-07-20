package com.emirkoral.deliveryapp.assignment.dto;

import java.time.LocalDateTime;

public record AssignmentResponse(
    Long id,
    Long orderId,
    Long courierId,
    String status,
    String message,
    LocalDateTime timestamp
) {} 