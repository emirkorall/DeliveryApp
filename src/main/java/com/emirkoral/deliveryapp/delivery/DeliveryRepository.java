package com.emirkoral.deliveryapp.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByDriverId(Long driverId);

    List<Delivery> findByStatus(Delivery.Status status);

    List<Delivery> findByDriverIdAndCreatedAtBetween(Long driverId, LocalDateTime start, LocalDateTime end);

    List<Delivery> findByOrderId(Long orderId);
}
