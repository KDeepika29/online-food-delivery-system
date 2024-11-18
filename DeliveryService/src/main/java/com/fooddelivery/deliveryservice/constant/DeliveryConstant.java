package com.fooddelivery.deliveryservice.constant;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DeliveryConstant {

	public enum Status {
		PICKED_UP("Picked Up"), PENDING("Pending"), ASSIGNED("Assigned"), DELIVERED("Delivered"), FAILED("Failed");

		public final String label;

		Status(String label) {
			this.label = label;
		}
		
		
		public static final String VALUES = Arrays.toString(values());
		
		public static void validateStatus(String status) {
	        try {
	        	Status.valueOf(status.toUpperCase());
	        } catch (IllegalArgumentException e) {
	            throw new ResponseStatusException(
	            		HttpStatus.BAD_REQUEST,
	                "Invalid status: " + status + ". Valid statuses are: " + VALUES
	            );
	        }
	    }

	}
	
	public enum Delivery_Person_Status {
		AVAILABLE("Available"), BUSY("Busy"), OFFLINE("Offline");

		public final String label;

		public static final String VALUES = Arrays.toString(values());
		Delivery_Person_Status(String label) {
			this.label = label;
		}
		
		public static void validateStatus(String status) {
	        try {
	            Delivery_Person_Status.valueOf(status.toUpperCase());
	        } catch (IllegalArgumentException e) {
	            throw new ResponseStatusException(
	            		HttpStatus.BAD_REQUEST,
	                "Invalid status: " + status + ". Valid statuses are: " + VALUES
	            );
	        }
	    }

	}

}
