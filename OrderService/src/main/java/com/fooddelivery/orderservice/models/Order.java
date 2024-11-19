package com.fooddelivery.orderservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
@RequiredArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @Column(name = "userId")
    @JsonProperty("userId")
    private Long userId;
    @Column(name = "restaurantId")
    @JsonProperty("restaurantId")
    private Long restaurantId;
    @Column(name = "orderDetails")
    @JsonProperty("orderDetails")
    private OrderDetails orderDetails;
    @Column(name = "status")
    @JsonProperty("status")
    private String status;
    @Column(name = "totalPrice")
    @JsonProperty("totalPrice")
    private double totalPrice;
    @Column(name = "paymentStatus")
    @JsonProperty("paymentStatus")
    private String paymentStatus;
}