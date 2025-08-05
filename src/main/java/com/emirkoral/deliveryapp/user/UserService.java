package com.emirkoral.deliveryapp.user;

import com.emirkoral.deliveryapp.user.dto.UserRequest;
import com.emirkoral.deliveryapp.user.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserResponse> findAllUsers();

    UserResponse findUserById(Long id);

    Optional<UserResponse> findByEmail(String email);

    Optional<UserResponse> findByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<UserResponse> findByRole(User.UserRole role);

    List<UserResponse> findByRoleAndIsActive(User.UserRole role, boolean isActive);

    List<UserResponse> findNearbyDrivers(double lat, double lng, double radiusInMeters);

    List<UserResponse> searchUsers(String email, String phone, User.UserRole role, Boolean isActive);

    UserResponse saveUser(UserRequest request);

    UserResponse updateUser(Long id, UserRequest userRequest);

    UserResponse deleteUserById(Long id);

}
