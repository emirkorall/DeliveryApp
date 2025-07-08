package com.emirkoral.deliveryapp.orderitem.mapper;


import com.emirkoral.deliveryapp.orderitem.OrderItem;
import com.emirkoral.deliveryapp.orderitem.dto.OrderItemRequest;
import com.emirkoral.deliveryapp.orderitem.dto.OrderItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "menuItem.id", source = "menuItemId")
    OrderItem toEntity(OrderItemRequest request);

    @Mapping(target = "menuItemId", source = "menuItem.id")
    OrderItemResponse toResponse(OrderItem orderItem);
}
