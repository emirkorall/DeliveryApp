package com.emirkoral.deliveryapp.delivery;

import com.emirkoral.deliveryapp.delivery.dto.DeliveryRequest;
import com.emirkoral.deliveryapp.delivery.dto.DeliveryResponse;
import com.emirkoral.deliveryapp.delivery.mapper.DeliveryMapper;
import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;
import com.emirkoral.deliveryapp.order.Order;
import com.emirkoral.deliveryapp.order.OrderRepository;
import com.emirkoral.deliveryapp.user.User;
import com.emirkoral.deliveryapp.user.UserRepository;
import com.emirkoral.deliveryapp.deliveryadress.DeliveryAddressRepository;
import com.emirkoral.deliveryapp.util.AuthorizationUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper, OrderRepository orderRepository, UserRepository userRepository, DeliveryAddressRepository deliveryAddressRepository) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.deliveryAddressRepository = deliveryAddressRepository;
    }

    @Override
    public List<DeliveryResponse> findAllDeliveries() {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        return deliveryRepository.findAll().stream()
                .map(deliveryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryResponse findDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + id));
        checkGeneralAccess(delivery.getOrder());
        return deliveryMapper.toResponse(delivery);
    }

    @Override
    public List<DeliveryResponse> findDeliveriesByDriverId(Long driverId) {
        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + driverId));
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), driver.getEmail());
        return deliveryRepository.findByDriverId(driverId).stream()
                .map(deliveryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryResponse> findDeliveriesByStatus(Delivery.Status status) {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        return deliveryRepository.findByStatus(status).stream()
                .map(deliveryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryResponse> findDeliveriesByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        checkGeneralAccess(order);
        return deliveryRepository.findByOrderId(orderId).stream()
                .map(deliveryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryResponse> findDeliveriesByDriverIdAndCreatedAtBetween(Long driverId, LocalDateTime start, LocalDateTime end) {
        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + driverId));
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), driver.getEmail());
        return deliveryRepository.findByDriverIdAndCreatedAtBetween(driverId, start, end)
                .stream()
                .map(deliveryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryResponse> findByDriverIdAndCreatedAtBetween(Long driverId, LocalDateTime start, LocalDateTime end) {
        User driver = userRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + driverId));
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), driver.getEmail());
        return deliveryRepository.findByDriverIdAndCreatedAtBetween(driverId, start, end).stream()
                .map(deliveryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryResponse saveDelivery(DeliveryRequest request) {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        Delivery delivery = buildDeliveryFromRequest(request);
        Delivery saved = deliveryRepository.save(delivery);
        return deliveryMapper.toResponse(saved);
    }

    @Override
    public DeliveryResponse updateDelivery(Long id, DeliveryRequest request) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + id));
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), delivery.getDriver().getEmail());

        deliveryMapper.updateEntityFromRequest(request, delivery);
        delivery.setOrder(orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + request.orderId())));
        delivery.setDriver(userRepository.findById(request.driverId())
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + request.driverId())));
        delivery.setDeliveryAddress(deliveryAddressRepository.findById(request.deliveryAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found with id: " + request.deliveryAddressId())));
        Delivery updated = deliveryRepository.save(delivery);
        return deliveryMapper.toResponse(updated);
    }

    @Override
    public DeliveryResponse deleteDeliveryById(Long id) {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + id));
        deliveryRepository.deleteById(id);
        return deliveryMapper.toResponse(delivery);
    }

    @Override
    public List<DeliveryResponse> searchDeliveries(Long driverId, Delivery.Status status, Long orderId, LocalDateTime start, LocalDateTime end) {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        return deliveryRepository.findAll().stream()
                .filter(d -> driverId == null || d.getDriver().getId().equals(driverId))
                .filter(d -> status == null || d.getStatus() == status)
                .filter(d -> orderId == null || d.getOrder().getId().equals(orderId))
                .filter(d -> start == null || !d.getCreatedAt().isBefore(start))
                .filter(d -> end == null || !d.getCreatedAt().isAfter(end))
                .map(deliveryMapper::toResponse)
                .collect(Collectors.toList());
    }

    private Delivery buildDeliveryFromRequest(DeliveryRequest request) {
        Delivery delivery = deliveryMapper.toEntity(request);
        delivery.setOrder(orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + request.orderId())));
        delivery.setDriver(userRepository.findById(request.driverId())
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + request.driverId())));
        delivery.setDeliveryAddress(deliveryAddressRepository.findById(request.deliveryAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found with id: " + request.deliveryAddressId())));
        return delivery;
    }

    private void checkGeneralAccess(Order order) {
        String customerEmail = order.getCustomer() != null ? order.getCustomer().getEmail() : null;
        String restaurantOwnerEmail = order.getRestaurant() != null && order.getRestaurant().getOwner() != null ?
                order.getRestaurant().getOwner().getEmail() : null;
        String driverEmail = order.getDelivery() != null && order.getDelivery().getDriver() != null ?
                order.getDelivery().getDriver().getEmail() : null;

        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN),
                customerEmail, restaurantOwnerEmail, driverEmail);
    }
}
