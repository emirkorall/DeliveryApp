package com.emirkoral.deliveryapp.auth;

import com.emirkoral.deliveryapp.auth.dto.SignupRequest;
import com.emirkoral.deliveryapp.auth.mapper.AuthMapper;
import com.emirkoral.deliveryapp.config.JwtUtil;
import com.emirkoral.deliveryapp.user.User;
import com.emirkoral.deliveryapp.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthMapper authMapper;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil();
        setField(jwtUtil, "secret", "dGVzdHNlY3JldHRlc3RzZWNyZXR0ZXN0c2VjcmV0dGVzdHNlY3JldA==");
        setField(jwtUtil, "expiration", 3600000L);
        setField(jwtUtil, "refreshExpiration", 7200000L);

        authService = new AuthServiceImpl(null, userDetailsService, jwtUtil, userRepository, passwordEncoder, authMapper);
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = ReflectionUtils.findField(target.getClass(), fieldName);
        ReflectionUtils.makeAccessible(field);
        field.set(target, value);
    }

    @Test
    void whenSignupWithValidRequest_thenCreateUserAndReturnTokens() {
        SignupRequest signupRequest = new SignupRequest("Test", "User", "test@test.com", "password", "1234567890", null);
        User user = new User();
        user.setEmail(signupRequest.email());
        user.setPassword("password");
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of());

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByPhone(any())).thenReturn(false);
        when(authMapper.toUser(any(SignupRequest.class))).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userDetailsService.loadUserByUsername(any())).thenReturn(userDetails);

        authService.signup(signupRequest);

        // we can add assertions here to verify the response
    }
} 