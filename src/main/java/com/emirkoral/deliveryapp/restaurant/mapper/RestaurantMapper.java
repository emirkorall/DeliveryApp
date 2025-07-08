package com.emirkoral.deliveryapp.restaurant.mapper;


import com.emirkoral.deliveryapp.restaurant.Restaurant;
import com.emirkoral.deliveryapp.restaurant.dto.RestaurantRequest;
import com.emirkoral.deliveryapp.restaurant.dto.RestaurantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner.id", source = "ownerId")
    Restaurant toEntity(RestaurantRequest request);

    @Mapping(target = "ownerId", source = "owner.id")
    RestaurantResponse toResponse(Restaurant restaurant);
}
