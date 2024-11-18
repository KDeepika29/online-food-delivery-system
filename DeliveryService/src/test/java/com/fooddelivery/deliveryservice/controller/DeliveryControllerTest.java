package com.fooddelivery.deliveryservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

import java.time.LocalDateTime;
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
import com.fooddelivery.deliveryservice.entity.DeliveriesEntity;
import com.fooddelivery.deliveryservice.service.DeliveryService;
import com.fooddelivery.deliveryservice.to.DeliveriesTO;

@EnableAspectJAutoProxy
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(DeliveryServiceController.class)
public class DeliveryControllerTest {

	@Autowired
	protected MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private DeliveryService service;

	@Test
	@Order(1)
	void tescreateDelivery() throws Exception {
		UUID uuid = UUID.randomUUID();

		DeliveriesEntity mockDeliveryEntity = new DeliveriesEntity();

		mockDeliveryEntity.setCreatedAt(LocalDateTime.now());

		mockDeliveryEntity.setDeliveryPersonId(uuid);
		mockDeliveryEntity.setEstimatedTime(LocalDateTime.now());
		mockDeliveryEntity.setId(uuid);
		mockDeliveryEntity.setLocation("Delhi");
		mockDeliveryEntity.setOrderId(uuid);
		mockDeliveryEntity.setStatus("Picked Up");
		mockDeliveryEntity.setUpdatedAt(LocalDateTime.now());
		doReturn(mockDeliveryEntity).when(service).createDelivery(Mockito.any());

		DeliveriesTO mockDeliveryTO = new DeliveriesTO();

		mockDeliveryTO.setCreatedAt(mockDeliveryEntity.getCreatedAt());
		mockDeliveryTO.setDeliveryPersonId(mockDeliveryEntity.getDeliveryPersonId());
		mockDeliveryTO.setEstimatedTime(mockDeliveryEntity.getEstimatedTime());
		mockDeliveryTO.setId(mockDeliveryEntity.getId());
		mockDeliveryTO.setOrderId(mockDeliveryEntity.getOrderId());
		mockDeliveryTO.setLocation(mockDeliveryEntity.getLocation());
		mockDeliveryTO.setStatus(mockDeliveryEntity.getStatus());
		mockDeliveryTO.setUpdatedAt(mockDeliveryEntity.getUpdatedAt());

		String content = mapper.writeValueAsString(mockDeliveryTO);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post("/delivery").contentType(MediaType.APPLICATION_JSON).content(content))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		Mockito.verify(service, times(1)).createDelivery(Mockito.any());
	}

	@Test
	@Order(2)
	void testGetDeliveryDetailsByOrderId() throws Exception {
		UUID uuid = UUID.randomUUID();

		DeliveriesEntity mockDeliveryEntity = new DeliveriesEntity();

		mockDeliveryEntity.setCreatedAt(LocalDateTime.now());

		mockDeliveryEntity.setDeliveryPersonId(uuid);
		mockDeliveryEntity.setEstimatedTime(LocalDateTime.now());
		mockDeliveryEntity.setId(uuid);
		mockDeliveryEntity.setLocation("Delhi");
		mockDeliveryEntity.setOrderId(uuid);
		mockDeliveryEntity.setStatus("Picked Up");
		mockDeliveryEntity.setUpdatedAt(LocalDateTime.now());

		DeliveriesTO mockDeliveryTO = new DeliveriesTO();

		mockDeliveryTO.setCreatedAt(mockDeliveryEntity.getCreatedAt());
		mockDeliveryTO.setDeliveryPersonId(mockDeliveryEntity.getDeliveryPersonId());
		mockDeliveryTO.setEstimatedTime(mockDeliveryEntity.getEstimatedTime());
		mockDeliveryTO.setId(mockDeliveryEntity.getId());
		mockDeliveryTO.setOrderId(mockDeliveryEntity.getOrderId());
		mockDeliveryTO.setLocation(mockDeliveryEntity.getLocation());
		mockDeliveryTO.setStatus(mockDeliveryEntity.getStatus());
		mockDeliveryTO.setUpdatedAt(mockDeliveryEntity.getUpdatedAt());

		doReturn(mockDeliveryTO).when(service).getDeliveryById(Mockito.any());

		String content = mapper.writeValueAsString(mockDeliveryTO);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get("/delivery/{id}",uuid).contentType(MediaType.APPLICATION_JSON).content(content))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		Mockito.verify(service, times(1)).getDeliveryById(Mockito.any());
	}
	
	@Test
	@Order(3)
	void testUpdateDeliveryStatus() throws Exception {
		UUID uuid = UUID.randomUUID();
		String status_string = "Picked Up";
		DeliveriesEntity mockDeliveryEntity = new DeliveriesEntity();

		mockDeliveryEntity.setCreatedAt(LocalDateTime.now());

		mockDeliveryEntity.setDeliveryPersonId(uuid);
		mockDeliveryEntity.setEstimatedTime(LocalDateTime.now());
		mockDeliveryEntity.setId(uuid);
		mockDeliveryEntity.setLocation("Delhi");
		mockDeliveryEntity.setOrderId(uuid);
		mockDeliveryEntity.setStatus("Picked Up");
		mockDeliveryEntity.setUpdatedAt(LocalDateTime.now());

		DeliveriesTO mockDeliveryTO = new DeliveriesTO();

		mockDeliveryTO.setCreatedAt(mockDeliveryEntity.getCreatedAt());
		mockDeliveryTO.setDeliveryPersonId(mockDeliveryEntity.getDeliveryPersonId());
		mockDeliveryTO.setEstimatedTime(mockDeliveryEntity.getEstimatedTime());
		mockDeliveryTO.setId(mockDeliveryEntity.getId());
		mockDeliveryTO.setOrderId(mockDeliveryEntity.getOrderId());
		mockDeliveryTO.setLocation(mockDeliveryEntity.getLocation());
		mockDeliveryTO.setStatus(mockDeliveryEntity.getStatus());
		mockDeliveryTO.setUpdatedAt(mockDeliveryEntity.getUpdatedAt());

		doReturn(mockDeliveryTO).when(service).updateDeliveryStatus(Mockito.any(),Mockito.anyString());

		String content = mapper.writeValueAsString(mockDeliveryTO);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.put("/delivery/{id}/status/{status}",uuid,status_string).contentType(MediaType.APPLICATION_JSON).content(content))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		Mockito.verify(service, times(1)).updateDeliveryStatus(Mockito.any(),Mockito.anyString());
	}
	

	@Test
	@Order(4)
	void testGetDeliveryByOrderId() throws Exception {
		UUID uuid = UUID.randomUUID();

		DeliveriesEntity mockDeliveryEntity = new DeliveriesEntity();

		mockDeliveryEntity.setCreatedAt(LocalDateTime.now());

		mockDeliveryEntity.setDeliveryPersonId(uuid);
		mockDeliveryEntity.setEstimatedTime(LocalDateTime.now());
		mockDeliveryEntity.setId(uuid);
		mockDeliveryEntity.setLocation("Delhi");
		mockDeliveryEntity.setOrderId(uuid);
		mockDeliveryEntity.setStatus("Picked Up");
		mockDeliveryEntity.setUpdatedAt(LocalDateTime.now());

		DeliveriesTO mockDeliveryTO = new DeliveriesTO();

		mockDeliveryTO.setCreatedAt(mockDeliveryEntity.getCreatedAt());
		mockDeliveryTO.setDeliveryPersonId(mockDeliveryEntity.getDeliveryPersonId());
		mockDeliveryTO.setEstimatedTime(mockDeliveryEntity.getEstimatedTime());
		mockDeliveryTO.setId(mockDeliveryEntity.getId());
		mockDeliveryTO.setOrderId(mockDeliveryEntity.getOrderId());
		mockDeliveryTO.setLocation(mockDeliveryEntity.getLocation());
		mockDeliveryTO.setStatus(mockDeliveryEntity.getStatus());
		mockDeliveryTO.setUpdatedAt(mockDeliveryEntity.getUpdatedAt());

		doReturn(mockDeliveryTO).when(service).getDeliveryByOrderId(Mockito.any());

		String content = mapper.writeValueAsString(mockDeliveryTO);

		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.get("/delivery/order/{order_id}",uuid).contentType(MediaType.APPLICATION_JSON).content(content))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		Mockito.verify(service, times(1)).getDeliveryByOrderId(Mockito.any());
	}

}
