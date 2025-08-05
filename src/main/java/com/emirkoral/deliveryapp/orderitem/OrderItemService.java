package com.emirkoral.deliveryapp.orderitem;

import com.emirkoral.deliveryapp.orderitem.dto.OrderItemRequest;
import com.emirkoral.deliveryapp.orderitem.dto.OrderItemResponse;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    List<OrderItemResponse> findAllOrderItems();

    OrderItemResponse findOrderItemById(Long id);

    List<OrderItemResponse> findOrderItemsByOrderId(Long orderId);

    List<OrderItemResponse> findOrderItemsByMenuItemId(Long menuItemId);

    OrderItemResponse saveOrderItem(OrderItemRequest request);

    OrderItemResponse updateOrderItem(Long id, OrderItemRequest request);

    OrderItemResponse deleteOrderItemById(Long id);

    List<OrderItemResponse> searchOrderItems(Long orderId, Long menuItemId, Integer minQuantity, Integer maxQuantity);


}
