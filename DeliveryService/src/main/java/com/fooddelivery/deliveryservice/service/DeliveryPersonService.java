package com.fooddelivery.deliveryservice.service;

import java.util.List;
import java.util.UUID;

import com.fooddelivery.deliveryservice.to.DeliveryPersonTO;

public interface DeliveryPersonService {
	
	 // Method to list all delivery personnel
    List<DeliveryPersonTO> getAllDeliveryPersons();

    // Method to update the status of a delivery person
    DeliveryPersonTO updateDeliveryPersonStatus(UUID id, String status) throws Exception;

}
