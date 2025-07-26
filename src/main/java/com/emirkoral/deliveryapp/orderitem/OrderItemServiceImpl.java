package com.emirkoral.deliveryapp.orderitem;


import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;

import com.emirkoral.deliveryapp.order.Order;
import com.emirkoral.deliveryapp.orderitem.dto.OrderItemRequest;
import com.emirkoral.deliveryapp.orderitem.dto.OrderItemResponse;
import com.emirkoral.deliveryapp.orderitem.mapper.OrderItemMapper;
import com.emirkoral.deliveryapp.user.User;
import com.emirkoral.deliveryapp.util.AuthorizationUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import java.util.stream.Collectors;
import com.emirkoral.deliveryapp.order.OrderRepository;
import com.emirkoral.deliveryapp.menuitem.MenuItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper, OrderRepository orderRepository, MenuItemRepository menuItemRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<OrderItemResponse> findAllOrderItems() {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        return orderItemRepository.findAll().stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemResponse findOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));
        checkOrderGeneralAccess(orderItem.getOrder());
        return orderItemMapper.toResponse(orderItem);
    }

    @Override
    public List<OrderItemResponse> findOrderItemsByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        checkOrderGeneralAccess(order);
        return orderItemRepository.findByOrderId(orderId).stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderItemResponse> findOrderItemsByMenuItemId(Long menuItemId) {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        return orderItemRepository.findByMenuItemId(menuItemId).stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemResponse saveOrderItem(OrderItemRequest request) {
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + request.orderId()));
        String customerEmail = order.getCustomer() != null ? order.getCustomer().getEmail() : null;
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), customerEmail);

        OrderItem orderItem = buildOrderItemFromRequest(request);
        OrderItem saved = orderItemRepository.save(orderItem);
        return orderItemMapper.toResponse(saved);
    }

    @Override
    public OrderItemResponse updateOrderItem(Long id, OrderItemRequest request) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));
        String customerEmail = orderItem.getOrder().getCustomer() != null ? orderItem.getOrder().getCustomer().getEmail() : null;
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), customerEmail);

        orderItemMapper.updateEntityFromRequest(request, orderItem);
        orderItem.setOrder(orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + request.orderId())));
        orderItem.setMenuItem(menuItemRepository.findById(request.menuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + request.menuItemId())));
        OrderItem saved = orderItemRepository.save(orderItem);
        return orderItemMapper.toResponse(saved);
    }

    @Override
    public OrderItemResponse deleteOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));
        String customerEmail = orderItem.getOrder().getCustomer() != null ? orderItem.getOrder().getCustomer().getEmail() : null;
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), customerEmail);

        orderItemRepository.deleteById(id);
        return orderItemMapper.toResponse(orderItem);

    }

    @Override
    public List<OrderItemResponse> searchOrderItems(Long orderId, Long menuItemId, Integer minQuantity, Integer maxQuantity) {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        return orderItemRepository.findAll().stream()
                .filter(oi -> orderId == null || (oi.getOrder() != null && Objects.equals(oi.getOrder().getId(), orderId)))
                .filter(oi -> menuItemId == null || (oi.getMenuItem() != null && Objects.equals(oi.getMenuItem().getId(), menuItemId)))
                .filter(oi -> minQuantity == null || (oi.getQuantity() != null && oi.getQuantity() >= minQuantity))
                .filter(oi -> maxQuantity == null || (oi.getQuantity() != null && oi.getQuantity() <= maxQuantity))
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    private OrderItem buildOrderItemFromRequest(OrderItemRequest request) {
        OrderItem orderItem = orderItemMapper.toEntity(request);
        orderItem.setOrder(orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + request.orderId())));
        orderItem.setMenuItem(menuItemRepository.findById(request.menuItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + request.menuItemId())));
        return orderItem;
    }

    private void checkOrderGeneralAccess(Order order) {
        String customerEmail = order.getCustomer() != null ? order.getCustomer().getEmail() : null;
        String restaurantOwnerEmail = order.getRestaurant() != null && order.getRestaurant().getOwner() != null ?
                order.getRestaurant().getOwner().getEmail() : null;
        String driverEmail = order.getDelivery() != null && order.getDelivery().getDriver() != null ?
                order.getDelivery().getDriver().getEmail() : null;

        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN),
                customerEmail, restaurantOwnerEmail, driverEmail);
    }
}
