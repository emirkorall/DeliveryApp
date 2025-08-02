package com.emirkoral.deliveryapp.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
@ActiveProfiles("test")
public class TestConfig {

    @Bean
    @Primary
    public String testJwtSecret() {
        return "dGVzdHNlY3JldHRlc3RzZWNyZXR0ZXN0c2VjcmV0dGVzdHNlY3JldA==";
    }

    @Bean
    @Primary
    public Long testJwtExpiration() {
        return 86400000L;
    }
} 