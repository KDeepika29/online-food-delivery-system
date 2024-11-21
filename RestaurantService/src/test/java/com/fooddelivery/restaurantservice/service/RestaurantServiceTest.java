package com.fooddelivery.restaurantservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import com.fooddelivery.restaurantservice.exceptions.CustomExceptions;
import com.fooddelivery.restaurantservice.dao.MenuCategoryDao;
import com.fooddelivery.restaurantservice.dao.RestaurantDao;
import com.fooddelivery.restaurantservice.entity.MenuCategoryEntity;
import com.fooddelivery.restaurantservice.entity.RestaurantEntity;
import com.fooddelivery.restaurantservice.to.MenuItemTO;
import com.fooddelivery.restaurantservice.to.RestaurantTO;

@SpringBootTest
public class RestaurantServiceTest {

	@MockBean
	RestaurantDao restaurantDao;
	
	@MockBean
	MenuCategoryDao menuCategoryDao;
	
	@MockBean
	MenuService menuService;
	
	@Autowired
	RestaurantService restaurantService;

	@Order(1)
	@Test
	void testGetRestaurantByOwnerId() throws CustomExceptions {
		var restaurantEntities = List.of(new RestaurantEntity());
		when(restaurantDao.findByOwnerId(Mockito.anyInt())).thenReturn(restaurantEntities);
		var restaurantTOs = restaurantService.getRestaurantByOwnerId(1);
		assertNotNull(restaurantTOs);
		assertEquals(1, restaurantTOs.size());
	}

	@Order(2)
	@Test
	void testGetAllRestaurants() throws Exception {
		var restaurantEntities = List.of(new RestaurantEntity());
		when(restaurantDao.findAll()).thenReturn(restaurantEntities);
		var restaurantTOs = restaurantService.getAllRestaurants();
		assertNotNull(restaurantTOs);
		assertEquals(1, restaurantTOs.size());
	}

	@Order(3)
	@Test
	void testCreateRestaurant_Success() throws Exception {
		RestaurantEntity restaurantEntity = new RestaurantEntity();
		restaurantEntity.setId(1);
		restaurantEntity.setRestaurantName("restaurant");

		when(restaurantDao.save(Mockito.any(RestaurantEntity.class))).thenReturn(restaurantEntity);

		RestaurantTO resturantTO = restaurantService.createRestaurant(new RestaurantTO());

		assertNotNull(resturantTO);
		assertEquals(restaurantEntity.getId(), resturantTO.getId());
		assertEquals(restaurantEntity.getRestaurantName(), resturantTO.getRestaurantName());
	}

	@Order(4)
	@Test
	void testCreateRestaurant_InvalidOwner() throws Exception {
		DataIntegrityViolationException dataIntegrityViolationException = new DataIntegrityViolationException("error",
				new ConstraintViolationException("error", null, "foriegn key"));
		
		when(restaurantDao.save(Mockito.any(RestaurantEntity.class))).thenThrow(dataIntegrityViolationException);

		RestaurantTO resturantTO = new RestaurantTO();
		resturantTO.setOwnerId(1);

		CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
			restaurantService.createRestaurant(resturantTO);
		});

		assertEquals("owner id = '1' is invalid", exception.getMessage());
	}

	@Order(5)
	@Test
	void testUpdateRestaurant_Success() throws Exception {
		RestaurantEntity restaurantEntity = new RestaurantEntity();
		restaurantEntity.setId(1);
		restaurantEntity.setRestaurantName("restaurant");

		when(restaurantDao.save(Mockito.any(RestaurantEntity.class))).thenReturn(restaurantEntity);

		RestaurantTO resturantTO = restaurantService.updateRestaurant(new RestaurantTO());

		assertNotNull(resturantTO);
		assertEquals(restaurantEntity.getId(), resturantTO.getId());
		assertEquals(restaurantEntity.getRestaurantName(), resturantTO.getRestaurantName());
	}
	
	@Order(6)
	@Test
	void testSearchRestaurant() throws CustomExceptions {
		
		var restaurantEntityA = new RestaurantEntity();		
		restaurantEntityA.setId(1);
		restaurantEntityA.setRestaurantName("Malabar");
		
		var restaurantEntityB = new RestaurantEntity();
		restaurantEntityB.setId(2);
		restaurantEntityB.setRestaurantName("NMR");
		
		var menuItemTO = new MenuItemTO();
					
		when(menuService.getMenuForRestaurant(Mockito.anyInt())).thenReturn(List.of(menuItemTO)).thenReturn(List.of());		
		when(restaurantDao.findAll()).thenReturn(List.of(restaurantEntityA, restaurantEntityB));
		
		var serachResult = restaurantService.searchRestaurant(Map.of("restaurantName", "Malabar"));
		
		assertEquals(1, serachResult.size());
		assertEquals("Malabar", serachResult.get(0).getRestaurantName());
	}

	@Order(7)
	@Test
	void testGetMenuCategories() throws Exception {		
		when(menuCategoryDao.findAll()).thenReturn(List.of(new MenuCategoryEntity()));		
		var menuCategories = restaurantService.getMenuCategories();		
		assertEquals(1, menuCategories.size());
	}
	
	@Order(8)
	@Test
	void testCheckIfRestaurantIdExist_Failure() throws Exception {

		when(restaurantDao.existsById(Mockito.anyInt())).thenReturn(false);
		
		CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
			restaurantService.checkIfRestaurantIdExist(-1);
		});

		assertEquals("restaurant id = '-1' is invalid", exception.getMessage());
	}
	
	@Order(9)
	@Test
	void testValidateRestaurantTO_MissingProperty() {		
		RestaurantTO restaurantTO = new RestaurantTO();
		
		CustomExceptions exception = assertThrows(CustomExceptions.class, () -> {
			restaurantService.validateRestaurantTO(restaurantTO);
		});

		assertEquals("Restaurant name is missing. Please provide restaurant name.", exception.getMessage());		
	}
}
