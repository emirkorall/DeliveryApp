package com.emirkoral.deliveryapp.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @GetMapping("/")
    public String root() {
        return "Delivery App is running!";
    }
} 