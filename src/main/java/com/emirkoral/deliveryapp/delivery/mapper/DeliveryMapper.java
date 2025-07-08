package com.emirkoral.deliveryapp.delivery.mapper;


import com.emirkoral.deliveryapp.delivery.Delivery;
import com.emirkoral.deliveryapp.delivery.dto.DeliveryRequest;
import com.emirkoral.deliveryapp.delivery.dto.DeliveryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order.id", source = "orderId")
    @Mapping(target = "driver.id", source = "driverId")
    @Mapping(target = "deliveryAddress.id", source = "deliveryAddressId")
    Delivery toEntity(DeliveryRequest request);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "driverId", source = "driver.id")
    @Mapping(target = "deliveryAddressId", source = "deliveryAddress.id")
    DeliveryResponse toResponse(Delivery delivery);


}
