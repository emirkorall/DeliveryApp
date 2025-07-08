package com.emirkoral.deliveryapp.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();

    Optional<Order> findById(Long id);

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByRestaurantId(Long restaurantId);

    List<Order> findByStatus(Order.Status status);

    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Order save(Order order);

    void deleteById(Long id);
}
