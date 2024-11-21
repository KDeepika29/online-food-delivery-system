package com.fooddelivery.deliveryservice.to;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class OrderTO {
	   @Id
	    @GeneratedValue(generator = "UUID")
	    @GenericGenerator(
	            name = "UUID",
	            strategy = "org.hibernate.id.UUIDGenerator"
	    )
	    @Column(name = "id", updatable = false, nullable = false)
	    @JsonProperty("id")
	    private UUID id;
	    @Column(name = "userId")
	    @JsonProperty("userId")
	    private UUID userId;
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
		public UUID getId() {
			return id;
		}
		public void setId(UUID id) {
			this.id = id;
		}
		public UUID getUserId() {
			return userId;
		}
		public void setUserId(UUID userId) {
			this.userId = userId;
		}
		public Long getRestaurantId() {
			return restaurantId;
		}
		public void setRestaurantId(Long restaurantId) {
			this.restaurantId = restaurantId;
		}
		public OrderDetails getOrderDetails() {
			return orderDetails;
		}
		public void setOrderDetails(OrderDetails orderDetails) {
			this.orderDetails = orderDetails;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public double getTotalPrice() {
			return totalPrice;
		}
		public void setTotalPrice(double totalPrice) {
			this.totalPrice = totalPrice;
		}
		public String getPaymentStatus() {
			return paymentStatus;
		}
		public void setPaymentStatus(String paymentStatus) {
			this.paymentStatus = paymentStatus;
		}

}
