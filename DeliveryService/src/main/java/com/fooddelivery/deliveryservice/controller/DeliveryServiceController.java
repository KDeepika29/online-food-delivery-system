package com.fooddelivery.deliveryservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.deliveryservice.entity.DeliveriesEntity;
import com.fooddelivery.deliveryservice.service.DeliveryService;
import com.fooddelivery.deliveryservice.to.DeliveriesTO;

@RestController
@ResponseBody
@RequestMapping("/delivery")
public class DeliveryServiceController {
	
	@Autowired
	DeliveryService deliveryService;
	
	 @PostMapping
	    public ResponseEntity<DeliveriesEntity> createDelivery(@RequestBody DeliveriesTO delivery) {
		 DeliveriesEntity createdDelivery = deliveryService.createDelivery(delivery);
	        return ResponseEntity.ok(createdDelivery);
	    }

	
	@GetMapping("/{id}")
	public ResponseEntity<DeliveriesTO> getDeliveryDetailsByOrderId(@PathVariable UUID id)  {

		DeliveriesTO delivery = deliveryService.getDeliveryById(id);
        return ResponseEntity.ok(delivery);
	}

	@PutMapping("/{id}/status/{status}")
    public ResponseEntity<DeliveriesTO> updateDeliveryStatus(@PathVariable UUID id, @PathVariable String status) {
		DeliveriesTO updatedDelivery = deliveryService.updateDeliveryStatus(id, status);
        return ResponseEntity.ok(updatedDelivery);
    }

    // GET /deliveries/order/{order_id}: Retrieve delivery details for a specific order
    @GetMapping("/order/{order_id}")
    public ResponseEntity<DeliveriesTO> getDeliveryByOrderId(@PathVariable UUID order_id) {
    	DeliveriesTO delivery = deliveryService.getDeliveryByOrderId(order_id);
        return ResponseEntity.ok(delivery);
    }
}
