package com.emirkoral.deliveryapp.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfoContributorConfig {
    @Bean
    public InfoContributor customInfoContributor() {
        return (Info.Builder builder) -> builder
            .withDetail("app", java.util.Map.of(
                "name", "DeliveryApp",
                "description", "Food delivery application",
                "version", "1.0.0"
            ));
    }
} 