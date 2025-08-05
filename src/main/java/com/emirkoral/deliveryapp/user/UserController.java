package com.emirkoral.deliveryapp.user;

import com.emirkoral.deliveryapp.user.dto.UserRequest;
import com.emirkoral.deliveryapp.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    // Search users by email, phone, role, and isActive
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) User.UserRole role,
            @RequestParam(required = false) Boolean isActive
    ) {
        List<UserResponse> results = userService.searchUsers(email, phone, role, isActive);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/drivers/nearby")
    public ResponseEntity<List<UserResponse>> getNearByDrivers(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam double radiusInMeters
    ) {
        List<UserResponse> drivers = userService.findNearbyDrivers(lat, lng, radiusInMeters);
        return ResponseEntity.ok(drivers);
    }

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest request) {
        UserResponse created = userService.saveUser(request);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        UserResponse updated = userService.updateUser(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
        UserResponse deleted = userService.deleteUserById(id);
        return ResponseEntity.ok(deleted);
    }


}
