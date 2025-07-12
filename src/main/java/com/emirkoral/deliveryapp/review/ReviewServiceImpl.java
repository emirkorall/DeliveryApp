package com.emirkoral.deliveryapp.review;

import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;
import com.emirkoral.deliveryapp.review.dto.ReviewRequest;
import com.emirkoral.deliveryapp.review.dto.ReviewResponse;
import com.emirkoral.deliveryapp.review.mapper.ReviewMapper;
import com.emirkoral.deliveryapp.user.UserRepository;
import com.emirkoral.deliveryapp.restaurant.RestaurantRepository;
import com.emirkoral.deliveryapp.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, 
                           UserRepository userRepository, RestaurantRepository restaurantRepository, 
                           OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<ReviewResponse> findAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse findReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        return reviewMapper.toResponse(review);
    }

    @Override
    public List<ReviewResponse> findReviewsByRestaurantId(Long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId).stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> findReviewsByCustomerId(Long customerId) {
        return reviewRepository.findByCustomerId(customerId).stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> findReviewsByOrderId(Long orderId) {
        return reviewRepository.findByOrderId(orderId).stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse createReview(ReviewRequest request) {
        Review review = buildReviewFromRequest(request);
        Review saved = reviewRepository.save(review);
        return reviewMapper.toResponse(saved);
    }

    @Override
    public ReviewResponse deleteReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        reviewRepository.deleteById(id);
        return reviewMapper.toResponse(review);
    }

    private Review buildReviewFromRequest(ReviewRequest request) {
        Review review = reviewMapper.toEntity(request);
        review.setCustomer(userRepository.findById(request.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.customerId())));
        review.setRestaurant(restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + request.restaurantId())));
        review.setOrder(orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + request.orderId())));
        return review;
    }
}
