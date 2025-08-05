package com.emirkoral.deliveryapp.restaurant;


import com.emirkoral.deliveryapp.restaurant.dto.RestaurantRequest;
import com.emirkoral.deliveryapp.restaurant.dto.RestaurantResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.findAllRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.findRestaurantById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResponse>> searchRestaurant(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) Boolean isActive
    ) {
        List<RestaurantResponse> results = restaurantService.searchRestaurants(name, cuisine, isActive);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/nearby")

    public ResponseEntity<List<RestaurantResponse>> getNearbyRestaurants(
            @RequestParam(required = false) double lat,
            @RequestParam(required = false) double lng,
            @RequestParam(required = false) double radiusInMeters
    ) {
        List<RestaurantResponse> results = restaurantService.findNearbyRestaurants(lat, lng, radiusInMeters);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@RequestBody @Valid RestaurantRequest request) {
        RestaurantResponse created = restaurantService.saveRestaurant(request);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable Long id, @RequestBody @Valid RestaurantRequest request) {
        RestaurantResponse updated = restaurantService.updateRestaurant(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestaurantResponse> deleteRestaurant(@PathVariable Long id) {
        RestaurantResponse deleted = restaurantService.deleteRestaurantById(id);
        return ResponseEntity.ok(deleted);

    }
}
