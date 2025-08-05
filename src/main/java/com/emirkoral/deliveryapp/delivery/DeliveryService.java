package com.emirkoral.deliveryapp.delivery;


import com.emirkoral.deliveryapp.delivery.dto.DeliveryRequest;
import com.emirkoral.deliveryapp.delivery.dto.DeliveryResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DeliveryService {
    List<DeliveryResponse> findAllDeliveries();

    DeliveryResponse findDeliveryById(Long id);

    List<DeliveryResponse> findDeliveriesByDriverId(Long driverId);

    List<DeliveryResponse> findDeliveriesByStatus(Delivery.Status status);

    List<DeliveryResponse> findDeliveriesByOrderId(Long orderId);

    List<DeliveryResponse> findDeliveriesByDriverIdAndCreatedAtBetween(Long driverId, LocalDateTime start, LocalDateTime end);

    List<DeliveryResponse> findByDriverIdAndCreatedAtBetween(Long driverId, LocalDateTime start, LocalDateTime end);

    DeliveryResponse saveDelivery(DeliveryRequest request);

    DeliveryResponse updateDelivery(Long id, DeliveryRequest request);

    DeliveryResponse deleteDeliveryById(Long id);

    List<DeliveryResponse> searchDeliveries(Long driverId, Delivery.Status status, Long orderId, LocalDateTime start, LocalDateTime end);


}
