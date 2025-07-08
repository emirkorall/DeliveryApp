package com.emirkoral.deliveryapp.delivery;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    @Override
    public Optional<Delivery> findById(Long id) {
        return deliveryRepository.findById(id);
    }

    @Override
    public List<Delivery> findByDriverId(Long driverId) {
        return deliveryRepository.findByDriverId(driverId);
    }

    @Override
    public List<Delivery> findByStatus(Delivery.Status status) {
        return deliveryRepository.findByStatus(status);
    }

    @Override
    public List<Delivery> findByOrderId(Long orderId) {
        return deliveryRepository.findByOrderId(orderId);
    }

    @Override
    public List<Delivery> findByDriverIdAndCreatedAtBetween(Long driverId, LocalDateTime start, LocalDateTime end) {
        return deliveryRepository.findByDriverIdAndCreatedAtBetween(driverId, start, end);
    }

    @Override
    public Delivery save(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public void deleteById(Long id) {
        deliveryRepository.deleteById(id);

    }
}
