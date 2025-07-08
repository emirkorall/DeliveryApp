package com.emirkoral.deliveryapp.payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<Payment> findAll();

    Optional<Payment> findById(Long id);

    Optional<Payment> findByOrderId(Long orderId);

    Payment save(Payment payment);

    void deleteById(Long id);
}
