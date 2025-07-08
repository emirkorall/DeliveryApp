package com.emirkoral.deliveryapp.deliveryadress;

import java.util.List;
import java.util.Optional;

public interface DeliveryAddressService {

    List<DeliveryAddress> findAll();

    Optional<DeliveryAddress> findById(Long id);

    DeliveryAddress save(DeliveryAddress deliveryAddress);

    void deleteById(Long id);
}
