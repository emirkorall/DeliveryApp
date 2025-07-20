package com.emirkoral.deliveryapp.order;

import com.emirkoral.deliveryapp.order.dto.OrderRequest;
import com.emirkoral.deliveryapp.order.dto.OrderResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    List<OrderResponse> findAllOrders();

    OrderResponse findOrderById(Long id);

    List<OrderResponse> findOrdersByCustomerId(Long customerId);

    List<OrderResponse> findOrdersByRestaurantId(Long restaurantId);

    List<OrderResponse> findOrdersByStatus(Order.Status status);

    List<OrderResponse> findOrdersByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    OrderResponse saveOrder(OrderRequest request);

    OrderResponse updateOrder(Long id, OrderRequest request);

    OrderResponse deleteOrderById(Long id);

    List<OrderResponse> searchOrders(Long customerId, Long restaurantId, Order.Status status, LocalDateTime start, LocalDateTime end);

    void updateOrderStatus(Long orderId, String newStatus, String message);
}
