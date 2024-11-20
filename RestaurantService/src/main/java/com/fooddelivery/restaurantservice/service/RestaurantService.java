package com.fooddelivery.restaurantservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.fooddelivery.restaurantservice.exceptions.CustomExceptions;
import com.fooddelivery.restaurantservice.dao.MenuCategoryDao;
import com.fooddelivery.restaurantservice.dao.RestaurantDao;
import com.fooddelivery.restaurantservice.entity.RestaurantEntity;
import com.fooddelivery.restaurantservice.to.MenuCategoryTO;
import com.fooddelivery.restaurantservice.to.MenuItemTO;
import com.fooddelivery.restaurantservice.to.RestaurantTO;

@Service
public class RestaurantService {

	@Autowired
	RestaurantDao restaurantDao;

	@Autowired
	MenuService menuService;

	@Autowired
	MenuCategoryDao menuCategoryDao;

	public List<RestaurantTO> getRestaurantByOwnerId(int ownerId) {		
		try {
			var restaurantEntities = restaurantDao.findByOwnerId(ownerId);
			return restaurantEntities.stream().map((restaurantEntity) -> {
				RestaurantTO restaurantTO = new RestaurantTO();
				BeanUtils.copyProperties(restaurantEntity, restaurantTO);
				return restaurantTO;
			}).toList();
		} catch (DataAccessException e) {
			throw new CustomExceptions("Error getting restaurant!- "+ e.getMessage());
		}
	}

	public List<RestaurantTO> getAllRestaurants() {
		try {
			return restaurantDao.findAll().stream().map((restaurantEntity) -> {
				RestaurantTO restaurantTO = new RestaurantTO();
				BeanUtils.copyProperties(restaurantEntity, restaurantTO);
				return restaurantTO;
			}).toList();
		} catch (DataAccessException e) {
			throw new CustomExceptions("Error getting all restaurants!- "+ e.getMessage());
		}
	}

	public RestaurantTO createRestaurant(RestaurantTO restaurantTO) {
		try {
			RestaurantEntity restaurantEntity = new RestaurantEntity();
			BeanUtils.copyProperties(restaurantTO, restaurantEntity);
			restaurantEntity = restaurantDao.save(restaurantEntity);
			BeanUtils.copyProperties(restaurantEntity, restaurantTO);
			return restaurantTO;
		} catch (DataAccessException e) {
			// checking for owner id constraint violation
			if(e.getCause() instanceof ConstraintViolationException ex) {
				System.out.println(ex.getMessage());
				String errorMsg = String.format("owner id = '%s' is invalid", restaurantTO.getOwnerId()); 
				throw new CustomExceptions(errorMsg);
			} else {
				System.out.println(e.getMessage());
				throw new CustomExceptions("Error creating restaurant!- "+ e.getMessage());				
			}
		}
	}

	public RestaurantTO updateRestaurant(RestaurantTO restaurantTO) {				
		return createRestaurant(restaurantTO);
	}

	public void deleteRestaurant(@PathVariable int restaurantId) {
		try {
			restaurantDao.deleteById(restaurantId);
		} catch (DataAccessException e) {
			System.out.println(e.getMessage());
			throw new CustomExceptions("Error deleting restaurant!- "+ e.getMessage());
		}
	}

	public List<RestaurantTO> searchRestaurant(Map<String, String> filter) {

		if (filter.isEmpty()) {
        return new ArrayList<>();
    	}

		String nameToFilterBy = filter.get("restaurantName");

		try {
			List<RestaurantEntity> restaurantEntities = restaurantDao.findAll();

            List<RestaurantEntity> filteredRestaurants = restaurantEntities.stream()
                .filter(restaurantEntity -> restaurantEntity.getRestaurantName().contains(nameToFilterBy))
                .collect(Collectors.toList());

            return filteredRestaurants.stream()
                .map(restaurantEntity -> {
                    RestaurantTO restaurantTO = new RestaurantTO();
                    BeanUtils.copyProperties(restaurantEntity, restaurantTO);
                    return restaurantTO;
                })
                .collect(Collectors.toList());
			
		} catch (DataAccessException e) {
			System.out.println(e.getMessage());
			throw new CustomExceptions("Error searching restaurants!- "+ e.getMessage());
		}
	}

	public List<MenuCategoryTO> getMenuCategories() {
		try {
			return menuCategoryDao.findAll().stream().map((menuCategoryEntity) -> {
				MenuCategoryTO menuCategoryTO = new MenuCategoryTO();
				BeanUtils.copyProperties(menuCategoryEntity, menuCategoryTO);
				return menuCategoryTO;
			}).toList();			
		} catch(DataAccessException e) {
			System.out.println(e.getMessage());
			throw new CustomExceptions("Error getting categories!- "+ e.getMessage());
		}
	}
	
	public void checkIfRestaurantIdExist(int restarauntId) {
		if(!restaurantDao.existsById(restarauntId)) {
			String errorMsg = String.format("restaurant id = '%s' is invalid", restarauntId);
			throw new CustomExceptions(errorMsg);
		}
	}
	
	public void validateRestaurantTO(RestaurantTO restaurantTO) {
		
		if(	Objects.isNull(restaurantTO.getRestaurantName())) {
			throw new CustomExceptions(HttpStatus.BAD_REQUEST, "Restaurant name is missing. Please provide restaurant name.");
		}
		
		if(Objects.isNull(restaurantTO.getAddress())) {
			throw new CustomExceptions(HttpStatus.BAD_REQUEST, "Restaurant address is missing. Please provide restaurant address.");			
		}
		
		if(Objects.isNull(restaurantTO.getOpeningHours())) {
			throw new CustomExceptions(HttpStatus.BAD_REQUEST, "Restaurant opening hour is missing. Please provide restaurant opening hour.");			
		}

		if(Objects.isNull(restaurantTO.getClosingHours())) {
			throw new CustomExceptions(HttpStatus.BAD_REQUEST, "Restaurant closing hour is missing. Please provide restaurant closing hour.");			
		}
		
		if(Objects.isNull(restaurantTO.getOwnerId())) {
			throw new CustomExceptions(HttpStatus.BAD_REQUEST, "Restaurant owner id is missing. Please provide restaurant owner id.");
		}		
	}
}
