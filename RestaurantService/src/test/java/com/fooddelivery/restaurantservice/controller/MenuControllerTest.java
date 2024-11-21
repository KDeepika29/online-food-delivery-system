package com.fooddelivery.restaurantservice.controller;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fooddelivery.restaurantservice.service.MenuService;
import com.fooddelivery.restaurantservice.to.MenuItemTO;
import com.fooddelivery.restaurantservice.to.SearchResultTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = {MenuController.class})
public class MenuControllerTest {

	@MockBean
	MenuService menuService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;

	@Order(1)
	@Test
	void testCreateMenu() throws Exception {
		var menuItemTO = new MenuItemTO();
		when(menuService.createMenu(Mockito.any())).thenReturn(menuItemTO);
		var requestBody = mapper.writeValueAsString(menuItemTO);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/menu").contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(MockMvcResultMatchers.status().isCreated());		
	}
	
	@Order(2)
	@Test
	void testUpdateMenu() throws Exception {
		var menuItemTO = new MenuItemTO();
		when(menuService.updateMenu(Mockito.any())).thenReturn(menuItemTO);
		var requestBody = mapper.writeValueAsString(menuItemTO);
		mockMvc.perform(
				MockMvcRequestBuilders.put("/menu/1").contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Order(3)
	@Test
	void testDeleteMenu() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/menu/1"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
