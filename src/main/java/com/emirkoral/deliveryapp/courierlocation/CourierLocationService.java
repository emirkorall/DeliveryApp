package com.emirkoral.deliveryapp.courierlocation;

import com.emirkoral.deliveryapp.courierlocation.dto.CourierLocationRequest;
import com.emirkoral.deliveryapp.courierlocation.dto.CourierLocationResponse;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourierLocationService {
    private final CourierLocationRepository courierLocationRepository;
    private final CourierLocationMapper courierLocationMapper;

    public CourierLocationService(CourierLocationRepository courierLocationRepository, CourierLocationMapper courierLocationMapper) {
        this.courierLocationRepository = courierLocationRepository;
        this.courierLocationMapper = courierLocationMapper;
    }

    public CourierLocationResponse saveLocation(CourierLocationRequest request) {
        CourierLocation location = courierLocationMapper.toEntity(request);
        location.setTimestamp(LocalDateTime.now());
        CourierLocation saved = courierLocationRepository.save(location);
        return courierLocationMapper.toResponse(saved);
    }

    public List<CourierLocationResponse> getLocationsByCourier(Long courierId) {
        return courierLocationRepository.findByCourierIdOrderByTimestampDesc(courierId)
                .stream()
                .map(courierLocationMapper::toResponse)
                .toList();
    }

    public List<CourierLocationResponse> getLocationsByOrder(Long orderId) {
        return courierLocationRepository.findByOrderIdOrderByTimestampDesc(orderId)
                .stream()
                .map(courierLocationMapper::toResponse)
                .toList();
    }
} 