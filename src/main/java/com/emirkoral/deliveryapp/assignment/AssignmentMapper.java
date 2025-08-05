package com.emirkoral.deliveryapp.assignment;

import com.emirkoral.deliveryapp.assignment.dto.AssignmentRequest;
import com.emirkoral.deliveryapp.assignment.dto.AssignmentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {
    Assignment toEntity(AssignmentRequest request);
    AssignmentResponse toResponse(Assignment assignment);
} 