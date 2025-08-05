package com.emirkoral.deliveryapp.payment.dto;

import com.emirkoral.deliveryapp.payment.Payment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,
        @NotBlank(message = "Payment method is required")
        Payment.PaymentMethod paymentMethod,
        @NotNull(message = "Order ID is required")
        Long orderId
) {
}
