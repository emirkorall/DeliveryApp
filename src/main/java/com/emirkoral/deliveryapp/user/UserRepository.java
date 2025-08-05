package com.emirkoral.deliveryapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
    Optional<User> findByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<User> findByRole(User.UserRole role);

    List<User> findByRoleAndIsActive(User.UserRole role, boolean isActive);

    // Geospatial: Find all active drivers near a location (in meters)
    @Query(value = "SELECT * FROM users u " +
            "WHERE u.role = :role " +
            "AND u.is_active = true " +
            "AND ST_DWithin(ST_MakePoint(u.longitude, u.latitude)::geography, " +
            "ST_MakePoint(:lng, :lat)::geography, :radius)", nativeQuery = true)
    List<User> findActiveDriversNearLocation(
            @Param("role") String role,
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("radius") double radiusInMeters
    );
    
}
