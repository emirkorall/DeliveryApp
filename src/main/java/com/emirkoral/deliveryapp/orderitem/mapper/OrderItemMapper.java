package com.emirkoral.deliveryapp.orderitem.mapper;


import com.emirkoral.deliveryapp.orderitem.OrderItem;
import com.emirkoral.deliveryapp.orderitem.dto.OrderItemRequest;
import com.emirkoral.deliveryapp.orderitem.dto.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItem toEntity(OrderItemRequest request);

    OrderItemResponse toResponse(OrderItem orderItem);

    void updateEntityFromRequest(OrderItemRequest request, @MappingTarget OrderItem orderItem);
}
