package com.emirkoral.deliveryapp.delivery;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DeliveryService {
    List<Delivery> findAll();

    Optional<Delivery> findById(Long id);

    List<Delivery> findByDriverId(Long driverId);

    List<Delivery> findByStatus(Delivery.Status status);

    List<Delivery> findByOrderId(Long orderId);

    List<Delivery> findByDriverIdAndCreatedAtBetween(Long driverId, LocalDateTime start, LocalDateTime end);

    Delivery save(Delivery delivery);

    void deleteById(Long id);
}
