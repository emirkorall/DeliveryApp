package com.emirkoral.deliveryapp.order;

import com.emirkoral.deliveryapp.order.dto.OrderRequest;
import com.emirkoral.deliveryapp.order.dto.OrderResponse;
import com.emirkoral.deliveryapp.order.mapper.OrderMapper;
import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;
import com.emirkoral.deliveryapp.user.UserRepository;
import com.emirkoral.deliveryapp.restaurant.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse findOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> findOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> findOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> findOrdersByStatus(Order.Status status) {
        return orderRepository.findByStatus(status).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> findOrdersByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByCreatedAtBetween(start, end).stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse saveOrder(OrderRequest request) {
        Order order = buildOrderFromRequest(request);
        Order saved = orderRepository.save(order);
        return orderMapper.toResponse(saved);
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderMapper.updateEntityFromRequest(request, order);
        order.setCustomer(userRepository.findById(request.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.customerId())));
        order.setRestaurant(restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + request.restaurantId())));
        Order updated = orderRepository.save(order);
        return orderMapper.toResponse(updated);
    }

    @Override
    public OrderResponse deleteOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.deleteById(id);
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> searchOrders(Long customerId, Long restaurantId, Order.Status status, LocalDateTime start, LocalDateTime end) {
        return orderRepository.findAll().stream()
                .filter(order -> customerId == null || (order.getCustomer() != null && order.getCustomer().getId().equals(customerId)))
                .filter(order -> restaurantId == null || (order.getRestaurant() != null && order.getRestaurant().getId().equals(restaurantId)))
                .filter(order -> status == null || (order.getStatus() != null && order.getStatus() == status))
                .filter(order -> start == null || (order.getCreatedAt() != null && !order.getCreatedAt().isBefore(start)))
                .filter(order -> end == null || (order.getCreatedAt() != null && !order.getCreatedAt().isAfter(end)))
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    private Order buildOrderFromRequest(OrderRequest request) {
        Order order = orderMapper.toEntity(request);
        order.setCustomer(userRepository.findById(request.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.customerId())));
        order.setRestaurant(restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + request.restaurantId())));
        return order;
    }
}
