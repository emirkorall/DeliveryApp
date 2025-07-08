package com.emirkoral.deliveryapp.order.dto;


import com.emirkoral.deliveryapp.orderitem.dto.OrderItemRequest;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotNull(message = "Customer ID is required")
        Long customerId,
        @NotNull(message = "Restaurant ID is required")
        Long restaurantId,
        @NotNull(message = "Order items are required")
        List<@NotNull OrderItemRequest> items,
        String specialInstruction
        ) {
}
