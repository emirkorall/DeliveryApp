package com.emirkoral.deliveryapp.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRestaurantId(Long restaurantId);

    List<Review> findByCustomerId(Long customerId);

    List<Review> findByOrderId(Long orderId);
}
