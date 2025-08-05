package com.emirkoral.deliveryapp.review;

import com.emirkoral.deliveryapp.review.dto.ReviewRequest;
import com.emirkoral.deliveryapp.review.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    List<ReviewResponse> findAllReviews();

    ReviewResponse findReviewById(Long id);

    List<ReviewResponse> findReviewsByRestaurantId(Long restaurantId);

    List<ReviewResponse> findReviewsByCustomerId(Long customerId);

    List<ReviewResponse> findReviewsByOrderId(Long orderId);

    ReviewResponse createReview(ReviewRequest request);

    ReviewResponse deleteReviewById(Long id);
}
