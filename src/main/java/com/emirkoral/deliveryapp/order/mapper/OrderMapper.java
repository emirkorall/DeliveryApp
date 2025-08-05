package com.emirkoral.deliveryapp.order.mapper;


import com.emirkoral.deliveryapp.order.Order;
import com.emirkoral.deliveryapp.order.dto.OrderRequest;
import com.emirkoral.deliveryapp.order.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderRequest request);

    OrderResponse toResponse(Order order);

    void updateEntityFromRequest(OrderRequest request, @MappingTarget Order order);
}
