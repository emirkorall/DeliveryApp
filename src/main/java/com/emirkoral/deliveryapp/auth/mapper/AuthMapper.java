package com.emirkoral.deliveryapp.auth.mapper;

import com.emirkoral.deliveryapp.auth.dto.SignupRequest;
import com.emirkoral.deliveryapp.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);
    User toUser(SignupRequest request);
} 