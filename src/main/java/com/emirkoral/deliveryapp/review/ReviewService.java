package com.emirkoral.deliveryapp.review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> findAll();

    Optional<Review> findById(Long id);

    List<Review> findByRestaurantId(Long restaurantId);

    List<Review> findByCustomerId(Long customerId);

    List<Review> findByOrderId(Long orderId);

    Review save(Review review);

    void deleteById(Long id);
}
