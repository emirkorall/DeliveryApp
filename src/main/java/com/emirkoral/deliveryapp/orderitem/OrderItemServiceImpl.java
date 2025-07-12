package com.emirkoral.deliveryapp.orderitem;


import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;

import com.emirkoral.deliveryapp.orderitem.dto.OrderItemRequest;
import com.emirkoral.deliveryapp.orderitem.dto.OrderItemResponse;
import com.emirkoral.deliveryapp.orderitem.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;

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
        return orderItemRepository.findAll().stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemResponse findOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));
        return orderItemMapper.toResponse(orderItem);
    }

    @Override
    public List<OrderItemResponse> findOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId).stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderItemResponse> findOrderItemsByMenuItemId(Long menuItemId) {
        return orderItemRepository.findByMenuItemId(menuItemId).stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemResponse saveOrderItem(OrderItemRequest request) {
        OrderItem orderItem = buildOrderItemFromRequest(request);
        OrderItem saved = orderItemRepository.save(orderItem);
        return orderItemMapper.toResponse(saved);
    }

    @Override
    public OrderItemResponse updateOrderItem(Long id, OrderItemRequest request) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));
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
        orderItemRepository.deleteById(id);
        return orderItemMapper.toResponse(orderItem);

    }

    @Override
    public List<OrderItemResponse> searchOrderItems(Long orderId, Long menuItemId, Integer minQuantity, Integer maxQuantity) {
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
}
