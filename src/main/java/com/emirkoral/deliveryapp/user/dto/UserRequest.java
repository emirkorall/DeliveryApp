package com.emirkoral.deliveryapp.user.dto;

import com.emirkoral.deliveryapp.user.User;
import jakarta.validation.constraints.*;

public record UserRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\\\+?[1-9]\\\\d{1,14}$\", message = \"Phone number should be valid")
        String phone,

        @NotNull(message = "Role is required")
        User.UserRole role


) {
}
