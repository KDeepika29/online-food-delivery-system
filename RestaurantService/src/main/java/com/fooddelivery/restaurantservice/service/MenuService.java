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

import com.fooddelivery.restaurantservice.exceptions.MenuCreationException;
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
    		throw new MenuCreationException("Error while creating menu! - " + e.getMessage());
    	}
    }

    public MenuItemTO updateMenu(MenuItemTO menuTO) {
    	return createMenu(menuTO);
    }

    public void deleteMenu(@PathVariable int menuId) {
    	try {
    		menuDao.deleteById(menuId);    		
    	} catch(DataAccessException e) {
    		throw new MenuCreationException("Error while deleting menu! - " + e.getMessage());
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
    		throw new MenuCreationException("Error while getting menu! - " + e.getMessage());
    	}
    }
    
    // public List<MenuItemTO> getMenuForRestaurant(int restaurantId, Map<String, String> filter) {
    	
    // 	try {			
    // 		List<MenuItemTO> menu = getMenuForRestaurant(restaurantId);
    		
    // 		List<MenuItemTO> filteredMenu = menu;
    		
    // 		List<Predicate<MenuItemTO>> menuMatchers = new ArrayList<>();
    		
    		
    // 		if(filter.containsKey("itemName")) {
    // 			String nameStr = filter.get("itemName");
    // 			menuMatchers.add((menuTO) -> {
    // 				return menuTO.getName().contains(nameStr);
    // 			});
    // 		}
    		
    // 		Predicate<MenuItemTO> menuMatcher = menuMatchers.stream().reduce(x -> true, Predicate::and);
    		
    // 		filteredMenu = filteredMenu.stream()
    // 				.filter(menuMatcher)
    // 				.toList();
    		
    // 		return filteredMenu;
    		
	// 	} catch (Exception e) {
	// 		throw e;}
	// 	catch(Exception e) {
	// 		throw new Exception("Error while getting menu! - " + e.getMessage());
	// 	}
    // }
    
    // public List<SearchResultTO> searchMenu(Map<String, String> filter) {
    	
    // 	List<SearchResultTO> searchResults = new ArrayList<>();
    	
    // 	if(filter.size() == 0) return searchResults;
    	
    // 	try {
    // 		List<RestaurantEntity> restaurantEntities = restaurantDao.findAll();
    		
    // 		for (RestaurantEntity restaurantEntity : restaurantEntities) {
    // 			List<MenuItemTO> filteredMenu = getMenuForRestaurant(restaurantEntity.getId(), filter);     		
    // 			if(filteredMenu.size() > 0) {
    // 				SearchResultTO searchResultTO = new SearchResultTO();
    // 				RestaurantTO restaurantTO = new RestaurantTO();
    // 				BeanUtils.copyProperties(restaurantEntity, restaurantTO);
    // 				searchResultTO.setRestaurant(restaurantTO);
    // 				searchResultTO.setMenu(filteredMenu);
    // 				searchResults.add(searchResultTO);
    // 			}				
	// 		}
    		    		
    // 		return searchResults;    		
    		
    // 	} catch(DataAccessException e) {
    // 		throw new Exception("Error while searching menu! - " + e.getMessage());
    // 	}
    // }
    
    private MenuItemTO convertMenuEntityToMenuTO(MenuItemEntity menuEntity) {
    	MenuItemTO menuTO = new MenuItemTO();
		BeanUtils.copyProperties(menuEntity, menuTO);		
    	return menuTO;
    }
    
    public void checkIfMenuItemIdExist(int menuItemId) {
    	if(!menuDao.existsById(menuItemId)) {
    		String errorMsg = String.format("Menu item id = '%s' does not exist", menuItemId);
    		throw new MenuCreationException(errorMsg);
    	}
    }
    
    public void validateMenuTO(MenuItemTO menuTO) {

    	if(Objects.isNull(menuTO.getItemName())) {
    		throw new Exception("Menu item name is missing. Please provide menu item name.");
    	}

    	if(Objects.isNull(menuTO.getRestaurantId())) {
    		throw new MenuCreationException("Restaurant id is missing. Please provide restaurant id.");
    	}
    	
    	if(Objects.isNull(menuTO.getCategoryId())) {
    		throw new MenuCreationException("Menu category id is missing. Please provide menu category id.");
    	}
    	
    	if(!restaurantDao.existsById(menuTO.getRestaurantId())) {
    		String errorMsg = String.format("Restaurant id = '%s' is invalid", menuTO.getRestaurantId());
    		throw new MenuCreationException(errorMsg);
    	}
    	
    	if(!menuCategoryDao.existsById(menuTO.getCategoryId())) {
    		String errorMsg = String.format("Menu category id = '%s' is invalid", menuTO.getCategoryId());
    		throw new MenuCreationException(errorMsg);
    	}
    }
}
