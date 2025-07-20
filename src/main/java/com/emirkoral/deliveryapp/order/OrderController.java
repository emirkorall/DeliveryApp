package com.emirkoral.deliveryapp.order;


import com.emirkoral.deliveryapp.order.dto.OrderRequest;
import com.emirkoral.deliveryapp.order.dto.OrderResponse;
import com.emirkoral.deliveryapp.order.dto.OrderStatusRequest;
import com.emirkoral.deliveryapp.order.dto.CourierLocationRequest;
import com.emirkoral.deliveryapp.assignment.dto.AssignmentRequest;
import com.emirkoral.deliveryapp.assignment.dto.AssignmentResponse;
import com.emirkoral.deliveryapp.assignment.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Autowired
    private AssignmentService assignmentService;

    public OrderController(OrderService orderService, SimpMessagingTemplate simpMessagingTemplate) {
        this.orderService = orderService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    private Bucket resolveOrderBucket(String ip) {
        return buckets.computeIfAbsent("order-" + ip, k -> Bucket4j.builder()
                .addLimit(Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1))))
                .build());
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrderResponse>> searchOrders(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(required = false) Order.Status status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end


    ) {
        List<OrderResponse> results = orderService.searchOrders(customerId, restaurantId, status, start, end);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request, HttpServletRequest httpRequest) {
        String ip = httpRequest.getRemoteAddr();
        Bucket bucket = resolveOrderBucket(ip);
        if (bucket.tryConsume(1)) {
            OrderResponse created = orderService.saveOrder(request);
            return ResponseEntity.status(201).body(created);
        } else {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many order attempts. Please try again later.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequest request) {
        OrderResponse updated = orderService.updateOrder(id, request);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestBody @Valid OrderStatusRequest request) {
        orderService.updateOrderStatus(id, request.status(), request.message());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/couriers/location")
    public ResponseEntity<Void> updateCourierLocation(@RequestBody @Valid CourierLocationRequest request) {

        String destination = "/topic/courier-location/" + request.orderId();
        simpMessagingTemplate.convertAndSend(destination, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<AssignmentResponse> assignOrderToCourier(@RequestBody @Valid AssignmentRequest request) {
        AssignmentResponse response = assignmentService.createAssignment(request);
        // WebSocket notification is used here to notify the courier about the new assignment in real time
        String destination = "/topic/assignments/" + response.courierId();
        simpMessagingTemplate.convertAndSend(destination, response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> deleteOrder(@PathVariable Long id) {
        OrderResponse deleted = orderService.deleteOrderById(id);
        return ResponseEntity.ok(deleted);
    }

    // Endpoint for live order list: returns the WebSocket channel for a specific restaurant
    @GetMapping("/restaurant/{restaurantId}/live")
    public ResponseEntity<String> getLiveOrderChannel(@PathVariable Long restaurantId) {
        // The frontend can use this endpoint to learn which WebSocket channel to subscribe to for live orders
        return ResponseEntity.ok("/topic/orders/" + restaurantId);
    }

}
