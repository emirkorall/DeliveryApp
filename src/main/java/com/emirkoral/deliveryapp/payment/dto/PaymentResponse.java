package com.emirkoral.deliveryapp.payment.dto;

import com.emirkoral.deliveryapp.payment.Payment;

import java.time.LocalDateTime;

public record PaymentResponse(Long id,
                              Double amount,
                              Payment.PaymentMethod paymentMethod,
                              Payment.Status status,
                              String transactionId,
                              LocalDateTime paymentDate,
                              Long orderId) {
}
