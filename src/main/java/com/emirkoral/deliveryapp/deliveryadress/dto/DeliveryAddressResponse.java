package com.emirkoral.deliveryapp.deliveryadress.dto;

public record DeliveryAddressResponse(Long id,
                                      String address,
                                      Double latitude,
                                      Double longitude,
                                      String apartment,
                                      String floor,
                                      String building,
                                      String instructions) {
}
