package com.emirkoral.deliveryapp.util;

import com.emirkoral.deliveryapp.exception.UnAuthorizedException;
import com.emirkoral.deliveryapp.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class AuthorizationUtil {
    public static void check(Set<User.UserRole> allowedRoles, String... allowedEmails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new UnAuthorizedException("Authentication object must not be null.");
        }

        final Set<User.UserRole> roles = (allowedRoles == null) ? Collections.emptySet() : allowedRoles;

        // Check for role-based authorization
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> roles.stream().anyMatch(r -> a.getAuthority().equals("ROLE_" + r.name())))) {
            return; // Authorized
        }

        // Check for email-based authorization
        if (Arrays.stream(allowedEmails)
                .filter(Objects::nonNull)
                .anyMatch(email -> authentication.getName().equals(email))) {
            return; // Authorized
        }

        // If neither check passes, throw exception
        throw new UnAuthorizedException("Not authorized.");
    }
}
