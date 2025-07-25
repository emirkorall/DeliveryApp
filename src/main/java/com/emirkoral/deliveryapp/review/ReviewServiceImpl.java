package com.emirkoral.deliveryapp.review;

import com.emirkoral.deliveryapp.exception.BadRequestException;
import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;
import com.emirkoral.deliveryapp.review.dto.ReviewRequest;
import com.emirkoral.deliveryapp.review.dto.ReviewResponse;
import com.emirkoral.deliveryapp.review.mapper.ReviewMapper;
import com.emirkoral.deliveryapp.user.UserRepository;
import com.emirkoral.deliveryapp.restaurant.RestaurantRepository;
import com.emirkoral.deliveryapp.order.OrderRepository;
import com.emirkoral.deliveryapp.util.AuthorizationUtil;
import com.emirkoral.deliveryapp.user.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse findReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        String customerEmail = review.getCustomer() != null ? review.getCustomer().getEmail() : null;
        String restaurantOwnerEmail = review.getRestaurant() != null && review.getRestaurant().getOwner() != null ?
                review.getRestaurant().getOwner().getEmail() : null;

        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN),
                customerEmail, restaurantOwnerEmail);
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
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), customer.getEmail());
        return reviewRepository.findByCustomerId(customerId).stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> findReviewsByOrderId(Long orderId) {
        Review review = reviewRepository.findByOrderId(orderId).stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Review not found for order id: " + orderId));
        String customerEmail = review.getCustomer() != null ? review.getCustomer().getEmail() : null;
        String restaurantOwnerEmail = review.getRestaurant() != null && review.getRestaurant().getOwner() != null ?
                review.getRestaurant().getOwner().getEmail() : null;

        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN),
                customerEmail, restaurantOwnerEmail);
        return reviewRepository.findByOrderId(orderId).stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse createReview(ReviewRequest request) {
        User customer = userRepository.findById(request.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.customerId()));
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), customer.getEmail());

        orderRepository.findById(request.orderId())
                .filter(order -> order.getCustomer().getId().equals(request.customerId()))
                .orElseThrow(() -> new BadRequestException("Order does not belong to the specified customer"));

        Review review = buildReviewFromRequest(request);
        Review saved = reviewRepository.save(review);
        return reviewMapper.toResponse(saved);
    }

    @Override
    public ReviewResponse deleteReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), review.getCustomer().getEmail());
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
