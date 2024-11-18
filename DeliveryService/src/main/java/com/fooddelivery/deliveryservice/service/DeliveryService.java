package com.fooddelivery.deliveryservice.service;

import java.util.UUID;

import com.fooddelivery.deliveryservice.entity.DeliveriesEntity;
import com.fooddelivery.deliveryservice.to.DeliveriesTO;

public interface DeliveryService {
	
	// Method to create a new delivery
	DeliveriesEntity createDelivery(DeliveriesTO delivery);

    // Method to get delivery details by ID
	DeliveriesTO getDeliveryById(UUID id);

    // Method to update the status of a delivery
	DeliveriesTO updateDeliveryStatus(UUID id, String status);

    // Method to get delivery details by order ID
	DeliveriesTO getDeliveryByOrderId(UUID orderId);

}
