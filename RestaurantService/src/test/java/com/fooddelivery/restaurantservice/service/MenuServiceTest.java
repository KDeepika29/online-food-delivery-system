package com.fooddelivery.restaurantservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.fooddelivery.restaurantservice.exceptions.CustomExceptions;
import com.fooddelivery.restaurantservice.dao.MenuCategoryDao;
import com.fooddelivery.restaurantservice.dao.MenuDao;
import com.fooddelivery.restaurantservice.dao.RestaurantDao;
import com.fooddelivery.restaurantservice.entity.MenuItemEntity;
import com.fooddelivery.restaurantservice.entity.RestaurantEntity;
import com.fooddelivery.restaurantservice.to.MenuItemTO;

@SpringBootTest
public class MenuServiceTest {
	
	@MockBean
	RestaurantDao restaurantDao;
	
	@MockBean
	MenuCategoryDao menuCategoryDao;
	
	@MockBean
	MenuDao menuDao;
	
	@SpyBean
	MenuService menuService;
		
	@Order(1)
	@Test
	void testCreateMenu() throws Exception {
		var menuItemEntity = new MenuItemEntity();
		menuItemEntity.setId(1);
		
		when(menuDao.save(Mockito.any())).thenReturn(menuItemEntity);
		
		var menuItemTO = new MenuItemTO();
		menuItemTO = menuService.createMenu(menuItemTO);
		
		assertNotNull(menuItemTO);
		assertEquals(menuItemEntity.getId(), menuItemTO.getId());
	}
	
	@Order(2)
	@Test
	void testUpdateMenu() throws Exception {
		var menuItemEntity = new MenuItemEntity();
		menuItemEntity.setId(1);
		
		when(menuDao.save(Mockito.any())).thenReturn(menuItemEntity);
		
		var menuItemTO = new MenuItemTO();
		menuItemTO = menuService.updateMenu(menuItemTO);
		
		assertNotNull(menuItemTO);
		assertEquals(menuItemEntity.getId(), menuItemTO.getId());
	}
	
	@Order(3)
	@Test
	void testGetMenuForRestaurant() throws Exception {
		var menuItemEntity = new MenuItemEntity();	
		when(menuDao.findByRestaurantId(Mockito.anyInt())).thenReturn(List.of(menuItemEntity));
		var menuItemTOs = menuService.getMenuForRestaurant(1);
		assertEquals(1, menuItemTOs.size());
	}
	
	@Order(5)
	@Test
    public void testCheckIfMenuItemIdExist_Failure() throws Exception {
		
		when(menuDao.existsById(Mockito.anyInt())).thenReturn(false);
		
		CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
			menuService.checkIfMenuItemIdExist(-1);
		});
				
		assertEquals("Menu item id = '-1' does not exist", exception.getMessage());
    }
	
	@Order(6)
	@Test
    public void testValidateMenuTO_MissingProperty() throws Exception {
		var menuItemTo = new MenuItemTO();

		CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
			menuService.validateMenuTO(menuItemTo);
		});
				
		assertEquals("Menu item name is missing. Please provide menu item name.", exception.getMessage());		
	}
}
