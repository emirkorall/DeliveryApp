package com.emirkoral.deliveryapp.restaurant.mapper;


import com.emirkoral.deliveryapp.restaurant.Restaurant;
import com.emirkoral.deliveryapp.restaurant.dto.RestaurantRequest;
import com.emirkoral.deliveryapp.restaurant.dto.RestaurantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    Restaurant toEntity(RestaurantRequest request);

    RestaurantResponse toResponse(Restaurant restaurant);

    void updateEntityFromRequest(RestaurantRequest request, @MappingTarget Restaurant restaurant);
}
