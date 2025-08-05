package com.emirkoral.deliveryapp.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId (Long customerId);

    List<Order> findByRestaurantId(Long restaurantId);

    List<Order> findByStatus(Order.Status status);

    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
