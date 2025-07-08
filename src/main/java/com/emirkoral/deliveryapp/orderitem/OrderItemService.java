package com.emirkoral.deliveryapp.orderitem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    List<OrderItem> findAll();

    Optional<OrderItem> findById(Long id);

    List<OrderItem> findOrderById(Long orderId);

    List<OrderItem> findByMenuItemId(Long menuItemId);

    OrderItem save(OrderItem orderItem);

    void deleteById(Long id);


}
