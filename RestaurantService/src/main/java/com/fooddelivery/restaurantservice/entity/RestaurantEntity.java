package com.fooddelivery.restaurantservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Restaurant", schema = "public")
@Entity
@Getter @Setter
public class RestaurantEntity {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column
	String restaurantName;
	
	@Column
	String address;
	
	@Column
	String openingHours;
	
	@Column
	String closingHours;
	
	@Column
	Integer ownerId;
}