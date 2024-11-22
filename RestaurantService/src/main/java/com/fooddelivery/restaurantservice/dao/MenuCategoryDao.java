package com.fooddelivery.restaurantservice.dao;

import org.springframework.data.repository.ListCrudRepository;

import com.fooddelivery.restaurantservice.entity.MenuCategoryEntity;

public interface MenuCategoryDao extends ListCrudRepository<MenuCategoryEntity, Integer> {

}
