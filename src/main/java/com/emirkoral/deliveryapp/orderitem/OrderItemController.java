package com.emirkoral.deliveryapp.orderitem;


import com.emirkoral.deliveryapp.orderitem.dto.OrderItemRequest;
import com.emirkoral.deliveryapp.orderitem.dto.OrderItemResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<List<OrderItemResponse>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.findAllOrderItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getOrderItemById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.findOrderItemById(id));
    }

    @GetMapping("/order/{orderId}")
    public List<OrderItemResponse> getOrderItemsByOrderId(@PathVariable Long orderId) {
        return orderItemService.findOrderItemsByOrderId(orderId);
    }


    @GetMapping("/menu-item/{menuItemId}")
    public List<OrderItemResponse> getOrderItemsByMenuItemId(@PathVariable Long menuItemId) {
        return orderItemService.findOrderItemsByMenuItemId(menuItemId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrderItemResponse>> searchOrderItems(
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) Long menuItemId,
            @RequestParam(required = false) Integer minQuantity,
            @RequestParam(required = false) Integer maxQuantity
    ) {
        List<OrderItemResponse> results = orderItemService.searchOrderItems(orderId, menuItemId, minQuantity, maxQuantity);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<OrderItemResponse> createOrderItem(@RequestBody @Valid OrderItemRequest request) {
        OrderItemResponse created = orderItemService.saveOrderItem(request);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(@PathVariable Long id, @RequestBody @Valid OrderItemRequest request) {
        OrderItemResponse updated = orderItemService.updateOrderItem(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderItemResponse> deleteOrderItem(@PathVariable Long id) {
        OrderItemResponse deleted = orderItemService.deleteOrderItemById(id);
        return ResponseEntity.ok(deleted);
    }
}
