package com.emirkoral.deliveryapp.delivery;


import com.emirkoral.deliveryapp.deliveryadress.DeliveryAddress;
import com.emirkoral.deliveryapp.order.Order;
import com.emirkoral.deliveryapp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "deliveries")
@EntityListeners(AuditingEntityListener.class)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime pickupTime;
    private LocalDateTime deliveryTime;
    private Integer estimatedDuration;
    private Integer actualDuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private Double currentLatitude;
    private Double currentLongitude;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private User driver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_address_id", nullable = false)
    private DeliveryAddress deliveryAddress;


    public enum Status {
        ASSIGNED,
        PICKED_UP,
        IN_TRANSIT,
        DELIVERED
    }
}
