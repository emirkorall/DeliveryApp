package com.emirkoral.deliveryapp.review.mapper;


import com.emirkoral.deliveryapp.review.Review;
import com.emirkoral.deliveryapp.review.dto.ReviewRequest;
import com.emirkoral.deliveryapp.review.dto.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "customerId")
    @Mapping(target = "restaurant.id", source = "restaurantId")
    @Mapping(target = "order.id", source = "orderId")
    Review toEntity(ReviewRequest request);

    @Mapping(target = "customerId", source = "user.id")
    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "orderId", source = "order.id")
    ReviewResponse toResponse(Review review);
}
