package com.emirkoral.deliveryapp.order.dto;

public record OrderStatusResponse(Long orderId, String status, String message) {}
