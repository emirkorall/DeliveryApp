package com.emirkoral.deliveryapp.review;

import com.emirkoral.deliveryapp.review.dto.ReviewRequest;
import com.emirkoral.deliveryapp.review.dto.ReviewResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        return ResponseEntity.ok(reviewService.findAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.findReviewById(id));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByRestaurantId(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(reviewService.findReviewsByRestaurantId(restaurantId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(reviewService.findReviewsByCustomerId(customerId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(reviewService.findReviewsByOrderId(orderId));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@RequestBody @Valid ReviewRequest request) {
        ReviewResponse created = reviewService.createReview(request);
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewResponse> deleteReview(@PathVariable Long id) {
        ReviewResponse deleted = reviewService.deleteReviewById(id);
        return ResponseEntity.ok(deleted);
    }
} 