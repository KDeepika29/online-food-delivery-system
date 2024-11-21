package com.fooddelivery.restaurantservice.dao;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.fooddelivery.restaurantservice.entity.MenuItemEntity;

public interface MenuDao extends ListCrudRepository<MenuItemEntity, Integer> {	
	List<MenuItemEntity> findByRestaurantId(int restaurantId);
}