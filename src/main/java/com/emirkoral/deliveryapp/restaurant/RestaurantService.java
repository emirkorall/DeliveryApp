package com.emirkoral.deliveryapp.restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    List<Restaurant> findAll();

    Optional<Restaurant> findById(Long id);

    List<Restaurant> findByOwnerId(Long ownerId);

    List<Restaurant> findByIsActive(boolean isActive);

    List<Restaurant> findNearbyRestaurants(double lat, double lng, double radiusInMeters);

    Restaurant save(Restaurant restaurant);

    void deleteById(Long id);
}
