package com.fooddelivery.restaurantservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.restaurantservice.service.MenuService;
import com.fooddelivery.restaurantservice.service.RestaurantService;
import com.fooddelivery.restaurantservice.entity.RestaurantEntity;
import com.fooddelivery.restaurantservice.entity.MenuCategoryEntity;
import com.fooddelivery.restaurantservice.to.RestaurantTO;
import com.fooddelivery.restaurantservice.to.MenuCategoryTO;
import com.fooddelivery.restaurantservice.to.MenuItemTO;


@RestController
@ResponseBody
@RequestMapping("/restaurant")
public class RestaurantController {
	
	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	MenuService menuService;
	
    @GetMapping
    public ResponseEntity<List<RestaurantTO>> getAllRestaurant() {    	
		var restaurants = restaurantService.getAllRestaurants();
		return ResponseEntity.ok(restaurants);
    }
	
    @GetMapping("/{ownerId}")
    public ResponseEntity<List<RestaurantTO>> getRestaurantByOwnerId(@PathVariable int ownerId) { 
		var restaurant = restaurantService.getRestaurantByOwnerId(ownerId);
		return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public ResponseEntity<RestaurantTO> createRestaurant(@RequestBody RestaurantTO restaurantTO) {
		restaurantService.validateRestaurantTO(restaurantTO);
		restaurantTO.setId(null);
		var createdRestaurant = restaurantService.createRestaurant(restaurantTO);
		return ResponseEntity.ok(createdRestaurant);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantTO> updateRestaurant(@RequestBody RestaurantTO restaurantTO, @PathVariable int restaurantId) {	
		restaurantService.validateRestaurantTO(restaurantTO);
		restaurantService.checkIfRestaurantIdExist(restaurantId);
		restaurantTO.setId(restaurantId);
		var updatedRestaurant = restaurantService.updateRestaurant(restaurantTO);
		return ResponseEntity.ok(updatedRestaurant);
    }
	
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable int restaurantId) {
		restaurantService.checkIfRestaurantIdExist(restaurantId);
		restaurantService.deleteRestaurant(restaurantId);
		return ResponseEntity.ok("Restaurant deleted successfully.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantTO>> searchRestaurant(@RequestParam Map<String, String> filter) {    	
		var restaurants = restaurantService.searchRestaurant(filter);
		return ResponseEntity.ok(restaurants);  		
    }

    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<MenuItemTO>> getRestaurantMenu(@PathVariable int restaurantId) {
		restaurantService.checkIfRestaurantIdExist(restaurantId);
		var menuItems = menuService.getMenuForRestaurant(restaurantId);
		return ResponseEntity.ok(menuItems); 	
    }
    
    @GetMapping("/menuCategories")
    public ResponseEntity<List<MenuCategoryTO>> getMenuCategories() {
		var menuCategories = restaurantService.getMenuCategories();
		return ResponseEntity.ok(menuCategories);
    }
}
