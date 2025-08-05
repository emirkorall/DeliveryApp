package com.emirkoral.deliveryapp.deliveryadress;

import com.emirkoral.deliveryapp.deliveryadress.dto.DeliveryAddressRequest;
import com.emirkoral.deliveryapp.deliveryadress.dto.DeliveryAddressResponse;

import java.util.List;

public interface DeliveryAddressService {
    List<DeliveryAddressResponse> findAllDeliveryAddresses();
    DeliveryAddressResponse findDeliveryAddressById(Long id);
    DeliveryAddressResponse saveDeliveryAddress(DeliveryAddressRequest request);
    DeliveryAddressResponse updateDeliveryAddress(Long id, DeliveryAddressRequest request);
    DeliveryAddressResponse deleteDeliveryAddressById(Long id);
    List<DeliveryAddressResponse> searchDeliveryAddresses(String address, Double latitude, Double longitude, String apartment, String floor, String building, String instructions);
}
