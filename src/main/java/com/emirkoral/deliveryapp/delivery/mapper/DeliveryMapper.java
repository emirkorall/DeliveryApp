package com.emirkoral.deliveryapp.delivery.mapper;


import com.emirkoral.deliveryapp.delivery.Delivery;
import com.emirkoral.deliveryapp.delivery.dto.DeliveryRequest;
import com.emirkoral.deliveryapp.delivery.dto.DeliveryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    Delivery toEntity(DeliveryRequest request);

    DeliveryResponse toResponse(Delivery delivery);


    void updateEntityFromRequest(DeliveryRequest request, @MappingTarget Delivery delivery);
}
