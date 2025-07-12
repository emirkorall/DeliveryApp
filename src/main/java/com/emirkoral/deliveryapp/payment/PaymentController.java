package com.emirkoral.deliveryapp.payment;

import com.emirkoral.deliveryapp.payment.dto.PaymentRequest;
import com.emirkoral.deliveryapp.payment.dto.PaymentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(paymentService.findAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.findPaymentById(id));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.findPaymentByOrderId(orderId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PaymentResponse>> searchPayments(
            @RequestParam(required = false) Double amount,
            @RequestParam(required = false) Payment.PaymentMethod paymentMethod,
            @RequestParam(required = false) Payment.Status status,
            @RequestParam(required = false) String transactionId,
            @RequestParam(required = false) Long orderId
    ) {
        List<PaymentResponse> results = paymentService.searchPayments(amount, paymentMethod, status, transactionId, orderId);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid PaymentRequest request) {
        PaymentResponse created = paymentService.savePayment(request);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable Long id, @RequestBody @Valid PaymentRequest request) {
        PaymentResponse updated = paymentService.updatePayment(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentResponse> deletePayment(@PathVariable Long id) {
        PaymentResponse deleted = paymentService.deletePaymentById(id);
        return ResponseEntity.ok(deleted);
    }
}
