package com.emirkoral.deliveryapp.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
        
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
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
    public List<User> findByRole(User.UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> findByRoleAndIsActive(User.UserRole role, boolean isActive) {
        return userRepository.findByRoleAndIsActive(role, isActive);
    }

    @Override
    public List<User> findNearbyDrivers(double lat, double lng, double radiusInMeters) {
        return userRepository.findActiveDriversNearLocation(
            User.UserRole.DRIVER.name(), lat, lng, radiusInMeters
        );
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
