package com.emirkoral.deliveryapp.order.dto;

import com.emirkoral.deliveryapp.orderitem.dto.OrderItemResponse;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long id,
                            String orderNumber,
                            String status,
                            Double totalAmount,
                            Double deliveryFee,
                            Double tax,
                            Double subtotal,
                            LocalDateTime estimatedDeliveryTime,
                            LocalDateTime actualDeliveryTime,
                            String specialInstructions,
                            Long customerId,
                            Long restaurantId,
                            List<OrderItemResponse> items) {
}
