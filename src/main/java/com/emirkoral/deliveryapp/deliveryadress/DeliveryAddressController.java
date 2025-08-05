package com.emirkoral.deliveryapp.deliveryadress;

import com.emirkoral.deliveryapp.deliveryadress.dto.DeliveryAddressRequest;
import com.emirkoral.deliveryapp.deliveryadress.dto.DeliveryAddressResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-addresses")
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    public DeliveryAddressController(DeliveryAddressService deliveryAddressService) {
        this.deliveryAddressService = deliveryAddressService;
    }

    @GetMapping
    public ResponseEntity<List<DeliveryAddressResponse>> getAllDeliveryAddresses() {
        return ResponseEntity.ok(deliveryAddressService.findAllDeliveryAddresses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryAddressResponse> getDeliveryAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryAddressService.findDeliveryAddressById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DeliveryAddressResponse>> searchDeliveryAddresses(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) String apartment,
            @RequestParam(required = false) String floor,
            @RequestParam(required = false) String building,
            @RequestParam(required = false) String instructions
    ) {
        List<DeliveryAddressResponse> results = deliveryAddressService.searchDeliveryAddresses(
                address, latitude, longitude, apartment, floor, building, instructions
        );
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<DeliveryAddressResponse> createDeliveryAddress(@RequestBody @Valid DeliveryAddressRequest request) {
        DeliveryAddressResponse created = deliveryAddressService.saveDeliveryAddress(request);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")

    public ResponseEntity<DeliveryAddressResponse> updateDeliveryAddress(@PathVariable Long id, @RequestBody @Valid DeliveryAddressRequest request) {
        DeliveryAddressResponse updated = deliveryAddressService.updateDeliveryAddress(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<DeliveryAddressResponse> deleteDeliveryAddress(@PathVariable Long id) {
        DeliveryAddressResponse deleted = deliveryAddressService.deleteDeliveryAddressById(id);
        return ResponseEntity.ok(deleted);
    }
}
