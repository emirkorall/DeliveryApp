package com.emirkoral.deliveryapp.restaurant;

import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;
import com.emirkoral.deliveryapp.restaurant.dto.RestaurantRequest;
import com.emirkoral.deliveryapp.restaurant.dto.RestaurantResponse;
import com.emirkoral.deliveryapp.restaurant.mapper.RestaurantMapper;
import com.emirkoral.deliveryapp.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final UserRepository userRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<RestaurantResponse> findAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantResponse findRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        return restaurantMapper.toResponse(restaurant);
    }

    @Override
    public List<RestaurantResponse> findByOwnerId(Long ownerId) {
        return restaurantRepository.findByOwnerId(ownerId)
                .stream()
                .map(restaurantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantResponse> findByIsActive(boolean isActive) {
        return restaurantRepository.findByIsActive(isActive)
                .stream()
                .map(restaurantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantResponse> findNearbyRestaurants(double lat, double lng, double radiusInMeters) {
        return restaurantRepository.findActiveRestaurantsNearLocation(lat, lng, radiusInMeters)
                .stream()
                .map(restaurantMapper::toResponse)
                .collect(Collectors.toList());
    }


    // Filters restaurants by optional name, cuisine, and active status, then maps them to response DTOs.
    @Override
    public List<RestaurantResponse> searchRestaurants(String name, String cuisine, Boolean isActive) {
        return restaurantRepository.findAll().stream()
                .filter(r -> name == null || r.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(r -> cuisine == null || (r.getCuisine() != null && r.getCuisine().equalsIgnoreCase(cuisine)))
                .filter(r -> isActive == null || r.getIsActive().equals(isActive))
                .map(restaurantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantResponse saveRestaurant(RestaurantRequest request) {
        Restaurant restaurant = buildRestaurantFromRequest(request);
        Restaurant saved = restaurantRepository.save(restaurant);
        return restaurantMapper.toResponse(saved);
    }

    @Override
    public RestaurantResponse updateRestaurant(Long id, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        restaurantMapper.updateEntityFromRequest(request, restaurant);
        restaurant.setOwner(userRepository.findById(request.ownerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + request.ownerId())));
        Restaurant updated = restaurantRepository.save(restaurant);
        return restaurantMapper.toResponse(updated);
    }

    @Override
    public RestaurantResponse deleteRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        restaurantRepository.deleteById(id);
        return restaurantMapper.toResponse(restaurant);

    }

    private Restaurant buildRestaurantFromRequest(RestaurantRequest request) {
        Restaurant restaurant = restaurantMapper.toEntity(request);
        restaurant.setOwner(userRepository.findById(request.ownerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + request.ownerId())));
        return restaurant;
    }
}
