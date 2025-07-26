package com.emirkoral.deliveryapp.deliveryadress;

import com.emirkoral.deliveryapp.deliveryadress.dto.DeliveryAddressRequest;
import com.emirkoral.deliveryapp.deliveryadress.dto.DeliveryAddressResponse;
import com.emirkoral.deliveryapp.deliveryadress.mapper.DeliveryAddressMapper;
import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;
import com.emirkoral.deliveryapp.util.AuthorizationUtil;
import com.emirkoral.deliveryapp.user.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;
    private final DeliveryAddressMapper deliveryAddressMapper;

    public DeliveryAddressServiceImpl(DeliveryAddressRepository deliveryAddressRepository, DeliveryAddressMapper deliveryAddressMapper) {
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.deliveryAddressMapper = deliveryAddressMapper;
    }

    @Override
    public List<DeliveryAddressResponse> findAllDeliveryAddresses() {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        return deliveryAddressRepository.findAll().stream()
                .map(deliveryAddressMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryAddressResponse findDeliveryAddressById(Long id) {
        DeliveryAddress address = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found with id: " + id));

        String customerEmail = address.getDelivery() != null && address.getDelivery().getOrder() != null &&
                address.getDelivery().getOrder().getCustomer() != null ?
                address.getDelivery().getOrder().getCustomer().getEmail() : null;
        String driverEmail = address.getDelivery() != null && address.getDelivery().getDriver() != null ?
                address.getDelivery().getDriver().getEmail() : null;

        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN), customerEmail, driverEmail);

        return deliveryAddressMapper.toResponse(address);
    }

    @Override
    public DeliveryAddressResponse saveDeliveryAddress(DeliveryAddressRequest request) {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        DeliveryAddress address = deliveryAddressMapper.toEntity(request);
        DeliveryAddress saved = deliveryAddressRepository.save(address);
        return deliveryAddressMapper.toResponse(saved);
    }

    @Override
    public DeliveryAddressResponse updateDeliveryAddress(Long id, DeliveryAddressRequest request) {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        DeliveryAddress address = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found with id: " + id));
        deliveryAddressMapper.updateEntityFromRequest(request, address);
        DeliveryAddress updated = deliveryAddressRepository.save(address);
        return deliveryAddressMapper.toResponse(updated);
    }

    @Override
    public DeliveryAddressResponse deleteDeliveryAddressById(Long id) {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        DeliveryAddress address = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found with id: " + id));
        deliveryAddressRepository.deleteById(id);
        return deliveryAddressMapper.toResponse(address);
    }

    @Override
    public List<DeliveryAddressResponse> searchDeliveryAddresses(String address, Double latitude, Double longitude, String apartment, String floor, String building, String instructions) {
        AuthorizationUtil.check(Collections.singleton(User.UserRole.ADMIN));
        return deliveryAddressRepository.findAll().stream()
                .filter(a -> address == null || (a.getAddress() != null && a.getAddress().toLowerCase().contains(address.toLowerCase())))
                .filter(a -> latitude == null || (a.getLatitude() != null && a.getLatitude().equals(latitude)))
                .filter(a -> longitude == null || (a.getLongitude() != null && a.getLongitude().equals(longitude)))
                .filter(a -> apartment == null || (a.getApartment() != null && a.getApartment().toLowerCase().contains(apartment.toLowerCase())))
                .filter(a -> floor == null || (a.getFloor() != null && a.getFloor().toLowerCase().contains(floor.toLowerCase())))
                .filter(a -> building == null || (a.getBuilding() != null && a.getBuilding().toLowerCase().contains(building.toLowerCase())))
                .filter(a -> instructions == null || (a.getInstructions() != null && a.getInstructions().toLowerCase().contains(instructions.toLowerCase())))
                .map(deliveryAddressMapper::toResponse)
                .collect(Collectors.toList());
    }
}
