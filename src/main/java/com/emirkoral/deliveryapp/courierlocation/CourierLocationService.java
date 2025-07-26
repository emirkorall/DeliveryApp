package com.emirkoral.deliveryapp.courierlocation;

import com.emirkoral.deliveryapp.courierlocation.dto.CourierLocationRequest;
import com.emirkoral.deliveryapp.courierlocation.dto.CourierLocationResponse;
import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;
import com.emirkoral.deliveryapp.order.Order;
import com.emirkoral.deliveryapp.order.OrderRepository;
import com.emirkoral.deliveryapp.user.User;
import com.emirkoral.deliveryapp.user.UserRepository;
import com.emirkoral.deliveryapp.util.AuthorizationUtil;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class CourierLocationService {
    private final CourierLocationRepository courierLocationRepository;
    private final CourierLocationMapper courierLocationMapper;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public CourierLocationService(CourierLocationRepository courierLocationRepository, CourierLocationMapper courierLocationMapper, UserRepository userRepository, OrderRepository orderRepository) {
        this.courierLocationRepository = courierLocationRepository;
        this.courierLocationMapper = courierLocationMapper;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public CourierLocationResponse saveLocation(CourierLocationRequest request) {
        User courier = userRepository.findById(request.courierId())
                .orElseThrow(() -> new ResourceNotFoundException("Courier not found with id: " + request.courierId()));
        AuthorizationUtil.check(Collections.emptySet(), courier.getEmail());

        CourierLocation location = courierLocationMapper.toEntity(request);
        location.setTimestamp(LocalDateTime.now());
        CourierLocation saved = courierLocationRepository.save(location);
        return courierLocationMapper.toResponse(saved);
    }

    public List<CourierLocationResponse> getLocationsByCourier(Long courierId) {
        User courier = userRepository.findById(courierId)
                .orElseThrow(() -> new ResourceNotFoundException("Courier not found with id: " + courierId));
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), courier.getEmail());

        return courierLocationRepository.findByCourierIdOrderByTimestampDesc(courierId)
                .stream()
                .map(courierLocationMapper::toResponse)
                .toList();
    }

    public List<CourierLocationResponse> getLocationsByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        String customerEmail = order.getCustomer() != null ? order.getCustomer().getEmail() : null;
        String restaurantOwnerEmail = order.getRestaurant() != null && order.getRestaurant().getOwner() != null ?
                order.getRestaurant().getOwner().getEmail() : null;

        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN),
                customerEmail, restaurantOwnerEmail);

        return courierLocationRepository.findByOrderIdOrderByTimestampDesc(orderId)
                .stream()
                .map(courierLocationMapper::toResponse)
                .toList();
    }
} 