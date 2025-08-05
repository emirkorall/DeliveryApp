package com.emirkoral.deliveryapp.payment;

import com.emirkoral.deliveryapp.payment.dto.PaymentRequest;
import com.emirkoral.deliveryapp.payment.dto.PaymentResponse;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<PaymentResponse> findAllPayments();

    PaymentResponse findPaymentById(Long id);

    PaymentResponse findPaymentByOrderId(Long orderId);

    PaymentResponse savePayment(PaymentRequest paymentRequest);

    PaymentResponse updatePayment(Long id, PaymentRequest request);

    PaymentResponse deletePaymentById(Long id);

    List<PaymentResponse> searchPayments(Double amount, Payment.PaymentMethod paymentMethod, Payment.Status status, String transactionId, Long orderId
    );
}
