package com.emirkoral.deliveryapp.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


    List<Restaurant> findByOwnerId(Long ownerId);

    List<Restaurant> findByIsActive(boolean isActive);

    // Geospatial: Find all active restaurants near a location (in meters)
    @Query(value = "SELECT * FROM restaurants r " +
            "WHERE r.is_active = true " +
            "AND ST_DWithin(ST_MakePoint(r.longitude, r.latitude)::geography, " +
            "ST_MakePoint(:lng, :lat)::geography, :radius)", nativeQuery = true)
    List<Restaurant> findActiveRestaurantsNearLocation(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("radius") double radiusInMeters
    );
}
