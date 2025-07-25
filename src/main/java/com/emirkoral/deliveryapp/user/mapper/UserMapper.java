package com.emirkoral.deliveryapp.user.mapper;

import com.emirkoral.deliveryapp.user.User;
import com.emirkoral.deliveryapp.user.dto.UserRequest;
import com.emirkoral.deliveryapp.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequest request);

    UserResponse toResponse(User user);


    void updateEntityFromRequest(UserRequest userRequest, @MappingTarget User user);
}
