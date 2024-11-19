package com.fooddelivery.deliveryservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.deliveryservice.service.DeliveryPersonService;
import com.fooddelivery.deliveryservice.to.DeliveryPersonTO;

@RestController
@RequestMapping("/delivery_persons")
public class DeliveryPersonController {
	
	@Autowired
    private DeliveryPersonService deliveryPersonService;

    // GET /delivery_persons: List all delivery personnel
    @GetMapping
    public ResponseEntity<List<DeliveryPersonTO>> getAllDeliveryPersons() {
        List<DeliveryPersonTO> deliveryPersons = deliveryPersonService.getAllDeliveryPersons();
        return ResponseEntity.ok(deliveryPersons);
    }

    // PUT /delivery_persons/{id}/status: Update the status of a delivery person
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<DeliveryPersonTO> updateDeliveryPersonStatus(@PathVariable UUID id, @PathVariable String status) throws Exception {
    	DeliveryPersonTO updatedDeliveryPerson = deliveryPersonService.updateDeliveryPersonStatus(id, status);
        return ResponseEntity.ok(updatedDeliveryPerson);
    }

}
