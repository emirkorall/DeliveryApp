package com.emirkoral.deliveryapp.deliveryadress;


import com.emirkoral.deliveryapp.delivery.Delivery;
import com.emirkoral.deliveryapp.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "delivery_addresses")
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    private Double latitude;
    private Double longitude;

    private String apartment;
    private String floor;
    private String building;
    private String instructions;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "deliveryAddress")
    private List<Order> order;

    @OneToOne(mappedBy = "deliveryAddress")
    private Delivery delivery;

}
