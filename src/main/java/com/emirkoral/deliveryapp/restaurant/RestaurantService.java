package com.emirkoral.deliveryapp.restaurant;

import com.emirkoral.deliveryapp.restaurant.dto.RestaurantRequest;
import com.emirkoral.deliveryapp.restaurant.dto.RestaurantResponse;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    List<RestaurantResponse> findAllRestaurants();

    RestaurantResponse findRestaurantById(Long id);

    List<RestaurantResponse> findByOwnerId(Long ownerId);

    List<RestaurantResponse> findByIsActive(boolean isActive);

    List<RestaurantResponse> findNearbyRestaurants(double lat, double lng, double radiusInMeters);

    List<RestaurantResponse> searchRestaurants(String name, String cuisineType, Boolean isActive);

    RestaurantResponse saveRestaurant(RestaurantRequest request);

    RestaurantResponse updateRestaurant(Long id, RestaurantRequest request);

    RestaurantResponse deleteRestaurantById(Long id);
}
