package com.emirkoral.deliveryapp.deliveryadress;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;

    public DeliveryAddressServiceImpl(DeliveryAddressRepository deliveryAddressRepository) {
        this.deliveryAddressRepository = deliveryAddressRepository;
    }

    @Override
    public List<DeliveryAddress> findAll() {
        return deliveryAddressRepository.findAll();
    }

    @Override
    public Optional<DeliveryAddress> findById(Long id) {
        return deliveryAddressRepository.findById(id);
    }

    @Override
    public DeliveryAddress save(DeliveryAddress deliveryAddress) {
        return deliveryAddressRepository.save(deliveryAddress);
    }

    @Override
    public void deleteById(Long id) {

        deliveryAddressRepository.deleteById(id);
    }
}
