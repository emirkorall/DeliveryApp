package com.emirkoral.deliveryapp.assignment;

import com.emirkoral.deliveryapp.assignment.dto.AssignmentRequest;
import com.emirkoral.deliveryapp.assignment.dto.AssignmentResponse;
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
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    public AssignmentService(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper, UserRepository userRepository, OrderRepository orderRepository) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public AssignmentResponse createAssignment(AssignmentRequest request) {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        Assignment assignment = assignmentMapper.toEntity(request);
        assignment.setTimestamp(LocalDateTime.now());
        Assignment saved = assignmentRepository.save(assignment);
        return assignmentMapper.toResponse(saved);
    }

    public List<AssignmentResponse> getAssignmentsByCourier(Long courierId) {
        User courier = userRepository.findById(courierId)
                .orElseThrow(() -> new ResourceNotFoundException("Courier not found with id: " + courierId));
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), courier.getEmail());

        return assignmentRepository.findByCourierId(courierId)
                .stream()
                .map(assignmentMapper::toResponse)
                .toList();
    }

    public List<AssignmentResponse> getAssignmentsByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        String customerEmail = order.getCustomer() != null ? order.getCustomer().getEmail() : null;
        String restaurantOwnerEmail = order.getRestaurant() != null && order.getRestaurant().getOwner() != null ?
                order.getRestaurant().getOwner().getEmail() : null;
        String driverEmail = order.getDelivery() != null && order.getDelivery().getDriver() != null ?
                order.getDelivery().getDriver().getEmail() : null;

        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN),
                customerEmail, restaurantOwnerEmail, driverEmail);

        return assignmentRepository.findByOrderId(orderId)
                .stream()
                .map(assignmentMapper::toResponse)
                .toList();
    }
} 