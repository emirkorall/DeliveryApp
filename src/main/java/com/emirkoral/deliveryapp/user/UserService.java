package com.emirkoral.deliveryapp.user;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<User> findByRole(User.UserRole role);

    List<User> findByRoleAndIsActive(User.UserRole role, boolean isActive);

    List<User> findNearbyDrivers(double lat, double lng, double radiusInMeters);

    User save(User user);

    void deleteById(Long id);

}
