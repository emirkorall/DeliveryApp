package com.emirkoral.deliveryapp.courierlocation;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourierLocationRepository extends JpaRepository<CourierLocation, Long> {
    List<CourierLocation> findByCourierIdOrderByTimestampDesc(Long courierId);
    List<CourierLocation> findByOrderIdOrderByTimestampDesc(Long orderId);
} 