package com.emirkoral.deliveryapp.auth;

import com.emirkoral.deliveryapp.auth.dto.AuthenticationRequest;
import com.emirkoral.deliveryapp.auth.dto.AuthenticationResponse;
import com.emirkoral.deliveryapp.auth.dto.SignupRequest;

public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse refresh(String refreshToken);
    AuthenticationResponse signup(SignupRequest request);
} 