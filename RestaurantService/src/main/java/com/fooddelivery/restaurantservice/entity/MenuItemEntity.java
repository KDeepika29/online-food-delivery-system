package com.fooddelivery.restaurantservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "MenuItem", schema = "public")
@Entity
@Getter @Setter
public class MenuItemEntity {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column
	Integer restaurantId;
	
	@Column
	Integer categoryId;
	
	@Column
	String itemName;
	
	@Column
	String description;
	
	@Column
	int price;
	
	@Column
	boolean available;
	
	@Column
	int preparationTime;
}
