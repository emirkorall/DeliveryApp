package com.emirkoral.deliveryapp.restaurant;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public List<Restaurant> findByOwnerId(Long ownerId) {
        return restaurantRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Restaurant> findByIsActive(boolean isActive) {
        return restaurantRepository.findByIsActive(isActive);
    }

    @Override
    public List<Restaurant> findNearbyRestaurants(double lat, double lng, double radiusInMeters) {
        return restaurantRepository.findActiveRestaurantsNearLocation(lat, lng, radiusInMeters);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteById(Long id) {
        restaurantRepository.deleteById(id);
    }
}
