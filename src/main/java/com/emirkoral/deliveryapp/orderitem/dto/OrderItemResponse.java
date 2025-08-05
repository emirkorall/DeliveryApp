package com.emirkoral.deliveryapp.orderitem.dto;

public record OrderItemResponse(Long id,
                                Long menuItemId,
                                Integer quantity,
                                Double unitPrice,
                                Double totalPrice,
                                String specialInstructions) {
}
