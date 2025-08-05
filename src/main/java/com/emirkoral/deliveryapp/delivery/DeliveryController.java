package com.emirkoral.deliveryapp.delivery;


import com.emirkoral.deliveryapp.delivery.dto.DeliveryRequest;
import com.emirkoral.deliveryapp.delivery.dto.DeliveryResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.findAllDeliveries());
    }

    @GetMapping("/{id}")

    public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.findDeliveryById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DeliveryResponse>> searchDeliveries(
            @RequestParam(required = false) Long driverId,
            @RequestParam(required = false) Delivery.Status status,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        List<DeliveryResponse> results = deliveryService.searchDeliveries(driverId, status, orderId, start, end);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(@RequestBody @Valid DeliveryRequest request) {
        DeliveryResponse created = deliveryService.saveDelivery(request);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")

    public ResponseEntity<DeliveryResponse> updateDelivery(@PathVariable Long id, @RequestBody @Valid DeliveryRequest request) {
        DeliveryResponse updated = deliveryService.updateDelivery(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<DeliveryResponse> deleteDelivery(@PathVariable Long id) {
        DeliveryResponse deleted = deliveryService.deleteDeliveryById(id);
        return ResponseEntity.ok(deleted);
    }
}
