package com.emirkoral.deliveryapp.deliveryadress.mapper;


import com.emirkoral.deliveryapp.deliveryadress.DeliveryAddress;
import com.emirkoral.deliveryapp.deliveryadress.dto.DeliveryAddressRequest;
import com.emirkoral.deliveryapp.deliveryadress.dto.DeliveryAddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeliveryAddressMapper {
    DeliveryAddress toEntity(DeliveryAddressRequest request);

    DeliveryAddressResponse toResponse(DeliveryAddress deliveryAddress);

    void updateEntityFromRequest(DeliveryAddressRequest request, @MappingTarget DeliveryAddress deliveryAddress);
}
