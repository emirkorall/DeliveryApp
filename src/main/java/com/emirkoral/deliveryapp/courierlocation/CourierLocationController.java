package com.emirkoral.deliveryapp.courierlocation;

import com.emirkoral.deliveryapp.courierlocation.dto.CourierLocationRequest;
import com.emirkoral.deliveryapp.courierlocation.dto.CourierLocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/courier-locations")
public class CourierLocationController {
    private final CourierLocationService courierLocationService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public CourierLocationController(CourierLocationService courierLocationService, SimpMessagingTemplate simpMessagingTemplate) {
        this.courierLocationService = courierLocationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping
    public ResponseEntity<CourierLocationResponse> saveLocation(@RequestBody @Valid CourierLocationRequest request) {
        CourierLocationResponse response = courierLocationService.saveLocation(request);
        String destination = "/topic/courier-location/" + response.orderId();
        simpMessagingTemplate.convertAndSend(destination, response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/courier/{courierId}")
    public ResponseEntity<List<CourierLocationResponse>> getLocationsByCourier(@PathVariable Long courierId) {
        return ResponseEntity.ok(courierLocationService.getLocationsByCourier(courierId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<CourierLocationResponse>> getLocationsByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(courierLocationService.getLocationsByOrder(orderId));
    }
} 