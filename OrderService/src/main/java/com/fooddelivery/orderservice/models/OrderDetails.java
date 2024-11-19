package com.fooddelivery.orderservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class OrderDetails {
    private String item;
    private int quantity;
    private double price;
}
