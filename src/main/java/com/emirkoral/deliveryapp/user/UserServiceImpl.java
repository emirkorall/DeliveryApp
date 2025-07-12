package com.emirkoral.deliveryapp.user;

import com.emirkoral.deliveryapp.exception.BadRequestException;
import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;
import com.emirkoral.deliveryapp.user.dto.UserRequest;
import com.emirkoral.deliveryapp.user.dto.UserResponse;
import com.emirkoral.deliveryapp.user.mapper.UserMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toResponse(user);
    }

    @Override
    public Optional<UserResponse> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toResponse);
    }

    @Override
    public Optional<UserResponse> findByPhone(String phone) {
        return userRepository.findByPhone(phone).map(userMapper::toResponse);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public List<UserResponse> findByRole(User.UserRole role) {
        return userRepository.findByRole(role)
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> findByRoleAndIsActive(User.UserRole role, boolean isActive) {
        return userRepository.findByRoleAndIsActive(role, isActive)
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> findNearbyDrivers(double lat, double lng, double radiusInMeters) {
        return userRepository.findActiveDriversNearLocation(
                        User.UserRole.DRIVER.name(), lat, lng, radiusInMeters
                ).stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse saveUser(UserRequest request) {
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        // Email and phone check if changed(uniqueness)
        if (!user.getEmail().equals(userRequest.email()) && userRepository.existsByEmail(userRequest.email())) {
            throw new BadRequestException("Email already in use");
        }
        if (!user.getPhone().equals(userRequest.phone()) && userRepository.existsByPhone(userRequest.phone())) {
            throw new BadRequestException("Phone already in use");
        }
        userMapper.updateEntityFromRequest(userRequest, user);
        User updated = userRepository.save(user);
        return userMapper.toResponse(updated);
    }

    @Override
    public UserResponse deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toResponse(user);
    }

    // Filters users by optional email, phone, role, and active status, then maps them to response DTOs.
    @Override
    public List<UserResponse> searchUsers(String email, String phone, User.UserRole role, Boolean isActive) {
        return userRepository.findAll().stream()
                .filter(user -> email == null || user.getEmail().equalsIgnoreCase(email))
                .filter(user -> phone == null || user.getPhone().equals(phone))
                .filter(user -> role == null || user.getRole() == role)
                .filter(user -> isActive == null || user.getIsActive().equals(isActive))
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }


}
