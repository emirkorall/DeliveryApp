package com.emirkoral.deliveryapp.auth.dto;

public record AuthenticationResponse(
    String accessToken,
    String refreshToken
) {} 