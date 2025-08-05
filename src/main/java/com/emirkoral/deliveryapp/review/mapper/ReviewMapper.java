package com.emirkoral.deliveryapp.review.mapper;


import com.emirkoral.deliveryapp.review.Review;
import com.emirkoral.deliveryapp.review.dto.ReviewRequest;
import com.emirkoral.deliveryapp.review.dto.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewRequest request);

    ReviewResponse toResponse(Review review);

    void updateEntityFromRequest(ReviewRequest request, @MappingTarget Review review);
}
