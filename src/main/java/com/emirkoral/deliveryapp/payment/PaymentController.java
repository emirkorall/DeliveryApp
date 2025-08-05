package com.emirkoral.deliveryapp.payment;

import com.emirkoral.deliveryapp.payment.dto.PaymentRequest;
import com.emirkoral.deliveryapp.payment.dto.PaymentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private Bucket resolvePaymentBucket(String ip) {
        return buckets.computeIfAbsent("payment-" + ip, k -> Bucket4j.builder()
                .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1))))
                .build());
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
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid PaymentRequest request, HttpServletRequest httpRequest) {
        String ip = httpRequest.getRemoteAddr();
        Bucket bucket = resolvePaymentBucket(ip);
        if (bucket.tryConsume(1)) {
            PaymentResponse created = paymentService.savePayment(request);
            return ResponseEntity.status(201).body(created);
        } else {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many payment attempts. Please try again later.");
        }
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
