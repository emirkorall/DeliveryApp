package com.emirkoral.deliveryapp.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByCourierId(Long courierId);
    List<Assignment> findByOrderId(Long orderId);
} 