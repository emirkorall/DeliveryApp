package com.emirkoral.deliveryapp.courierlocation;

import com.emirkoral.deliveryapp.courierlocation.dto.CourierLocationRequest;
import com.emirkoral.deliveryapp.courierlocation.dto.CourierLocationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourierLocationMapper {
    CourierLocation toEntity(CourierLocationRequest request);
    CourierLocationResponse toResponse(CourierLocation courierLocation);
} 