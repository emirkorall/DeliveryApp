package com.emirkoral.deliveryapp.payment.mapper;


import com.emirkoral.deliveryapp.order.Order;
import com.emirkoral.deliveryapp.payment.Payment;
import com.emirkoral.deliveryapp.payment.dto.PaymentRequest;
import com.emirkoral.deliveryapp.payment.dto.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order.id", source = "orderId")
    Payment toEntity(PaymentRequest request);

    @Mapping(target = "orderId", source = "order.id")
    PaymentResponse toResponse(Payment payment);
}
