package com.fooddelivery.restaurantservice.dao;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.fooddelivery.restaurantservice.entity.RestaurantEntity;

public interface RestaurantDao extends ListCrudRepository<RestaurantEntity, Integer> {	
	List<RestaurantEntity> findByOwnerId(int ownerId);
}
