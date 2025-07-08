package com.emirkoral.deliveryapp.order.mapper;


import com.emirkoral.deliveryapp.order.Order;
import com.emirkoral.deliveryapp.order.dto.OrderRequest;
import com.emirkoral.deliveryapp.order.dto.OrderResponse;
import com.emirkoral.deliveryapp.orderitem.mapper.OrderItemMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "customerId")
    @Mapping(target = "restaurant.id", source = "restaurantId")
    @Mapping(target = "orderItems", source = "items")
    Order toEntity(OrderRequest request);


    @Mapping(target = "customerId", source = "user.id")
    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "items", source = "orderItems")
    OrderResponse toResponse(Order order);
}
