package com.fooddelivery.deliveryservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddelivery.deliveryservice.service.DeliveryPersonService;
import com.fooddelivery.deliveryservice.to.DeliveryPersonTO;

@EnableAspectJAutoProxy
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(DeliveryPersonController.class)
public class DeliveryPersonControllerTest {

	@Autowired
	protected MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private DeliveryPersonService service;
	
	@Test
	@Order(1)
	void testGetAllDeliveryPersons() throws Exception {
		UUID uuid = UUID.randomUUID();
		
		DeliveryPersonTO personTO = new DeliveryPersonTO();
		personTO.setCreatedAt(LocalDateTime.now());
		personTO.setId(uuid);
		personTO.setName("Akshara");
		personTO.setPhone("123456789");
		personTO.setStatus("Available");
		personTO.setUpdatedAt(LocalDateTime.now());
		

		doReturn(List.of(personTO)).when(service).getAllDeliveryPersons();

		String content = mapper.writeValueAsString(List.of(personTO));

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get("/delivery_persons").contentType(MediaType.APPLICATION_JSON).content(content))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		Mockito.verify(service, times(1)).getAllDeliveryPersons();
	}
	
	@Test
	@Order(2)
	void testUpdateDeliveryPersonStatus() throws Exception {
		UUID uuid = UUID.randomUUID();
		String status_string = "Available";
		
		DeliveryPersonTO personTO = new DeliveryPersonTO();
		personTO.setCreatedAt(LocalDateTime.now());
		personTO.setId(uuid);
		personTO.setName("Akshara");
		personTO.setPhone("123456789");
		personTO.setStatus("Available");
		personTO.setUpdatedAt(LocalDateTime.now());
		

		doReturn(personTO).when(service).updateDeliveryPersonStatus(Mockito.any(),Mockito.anyString());

		String content = mapper.writeValueAsString(personTO);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put("/delivery_persons/{id}/status/{status}",uuid,status_string).contentType(MediaType.APPLICATION_JSON).content(content))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		Mockito.verify(service, times(1)).updateDeliveryPersonStatus(Mockito.any(),Mockito.anyString());
	}
}
