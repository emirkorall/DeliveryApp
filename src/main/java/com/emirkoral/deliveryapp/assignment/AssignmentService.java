package com.emirkoral.deliveryapp.assignment;

import com.emirkoral.deliveryapp.assignment.dto.AssignmentRequest;
import com.emirkoral.deliveryapp.assignment.dto.AssignmentResponse;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    public AssignmentService(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
    }

    public AssignmentResponse createAssignment(AssignmentRequest request) {
        Assignment assignment = assignmentMapper.toEntity(request);
        assignment.setTimestamp(LocalDateTime.now());
        Assignment saved = assignmentRepository.save(assignment);
        return assignmentMapper.toResponse(saved);
    }

    public List<AssignmentResponse> getAssignmentsByCourier(Long courierId) {
        return assignmentRepository.findByCourierId(courierId)
                .stream()
                .map(assignmentMapper::toResponse)
                .toList();
    }

    public List<AssignmentResponse> getAssignmentsByOrder(Long orderId) {
        return assignmentRepository.findByOrderId(orderId)
                .stream()
                .map(assignmentMapper::toResponse)
                .toList();
    }
} 