package com.emirkoral.deliveryapp.payment.mapper;


import com.emirkoral.deliveryapp.order.Order;
import com.emirkoral.deliveryapp.payment.Payment;
import com.emirkoral.deliveryapp.payment.dto.PaymentRequest;
import com.emirkoral.deliveryapp.payment.dto.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toEntity(PaymentRequest request);

    PaymentResponse toResponse(Payment payment);

    void updateEntityFromRequest(PaymentRequest request, @MappingTarget Payment payment);
}
