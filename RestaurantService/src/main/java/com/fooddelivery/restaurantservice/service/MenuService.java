package com.fooddelivery.restaurantservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.fooddelivery.restaurantservice.exceptions.CustomExceptions;
import com.fooddelivery.restaurantservice.dao.MenuCategoryDao;
import com.fooddelivery.restaurantservice.dao.MenuDao;
import com.fooddelivery.restaurantservice.dao.RestaurantDao;
import com.fooddelivery.restaurantservice.entity.MenuItemEntity;
import com.fooddelivery.restaurantservice.entity.RestaurantEntity;
import com.fooddelivery.restaurantservice.to.MenuItemTO;
import com.fooddelivery.restaurantservice.to.RestaurantTO;
import com.fooddelivery.restaurantservice.to.SearchResultTO;

@Service
public class MenuService {
	
	@Autowired
	MenuDao menuDao;
	
	@Autowired
	RestaurantDao restaurantDao;
	
	@Autowired
	MenuCategoryDao menuCategoryDao;
	
	
    public MenuItemTO createMenu(MenuItemTO menuTO) {
    	try {
    		MenuItemEntity menuEntity = new MenuItemEntity();    		
    		BeanUtils.copyProperties(menuTO, menuEntity);
    		menuEntity = menuDao.save(menuEntity);
    		return convertMenuEntityToMenuTO(menuEntity);
    	} catch(DataAccessException e) {
    		throw new CustomExceptions("Error while creating menu! - " + e.getMessage());
    	}
    }

    public MenuItemTO updateMenu(MenuItemTO menuTO) {
    	return createMenu(menuTO);
    }

    public void deleteMenu(@PathVariable int menuId) {
    	try {
    		menuDao.deleteById(menuId);    		
    	} catch(DataAccessException e) {
    		throw new CustomExceptions("Error while deleting menu! - " + e.getMessage());
    	}
    }
    
    public List<MenuItemTO> getMenuForRestaurant(int restaurantId)  {
    	try {
    		List<MenuItemEntity> menuEntities = menuDao.findByRestaurantId(restaurantId);
    		return menuEntities.stream().map((menuEntity) -> {
    			MenuItemTO menuTO = convertMenuEntityToMenuTO(menuEntity);
    			return menuTO;
    		}).toList();    		
    	} catch(DataAccessException e) {
    		throw new CustomExceptions("Error while getting menu! - " + e.getMessage());
    	}
    }
    
    private MenuItemTO convertMenuEntityToMenuTO(MenuItemEntity menuEntity) {
    	MenuItemTO menuTO = new MenuItemTO();
		BeanUtils.copyProperties(menuEntity, menuTO);		
    	return menuTO;
    }
    
    public void checkIfMenuItemIdExist(int menuItemId) {
    	if(!menuDao.existsById(menuItemId)) {
    		String errorMsg = String.format("Menu item id = '%s' does not exist", menuItemId);
    		throw new CustomExceptions(errorMsg);
    	}
    }
    
    public void validateMenuTO(MenuItemTO menuTO) {

    	if(Objects.isNull(menuTO.getItemName())) {
    		throw new CustomExceptions("Menu item name is missing. Please provide menu item name.");
    	}

    	if(Objects.isNull(menuTO.getRestaurantId())) {
    		throw new CustomExceptions("Restaurant id is missing. Please provide restaurant id.");
    	}
    	
    	if(Objects.isNull(menuTO.getCategoryId())) {
    		throw new CustomExceptions("Menu category id is missing. Please provide menu category id.");
    	}
    	
    	if(!restaurantDao.existsById(menuTO.getRestaurantId())) {
    		String errorMsg = String.format("Restaurant id = '%s' is invalid", menuTO.getRestaurantId());
    		throw new CustomExceptions(errorMsg);
    	}
    	
    	if(!menuCategoryDao.existsById(menuTO.getCategoryId())) {
    		String errorMsg = String.format("Menu category id = '%s' is invalid", menuTO.getCategoryId());
    		throw new CustomExceptions(errorMsg);
    	}
    }
}
