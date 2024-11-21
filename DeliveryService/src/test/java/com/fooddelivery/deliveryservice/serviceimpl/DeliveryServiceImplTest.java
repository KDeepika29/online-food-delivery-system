package com.fooddelivery.deliveryservice.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddelivery.deliveryservice.dao.DeliveriesDao;
import com.fooddelivery.deliveryservice.entity.DeliveriesEntity;
import com.fooddelivery.deliveryservice.to.DeliveriesTO;
import com.fooddelivery.deliveryservice.to.OrderTO;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters=false)
@WebMvcTest(DeliveryServiceImpl.class)
public class DeliveryServiceImplTest {
	
	@Autowired
	protected MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private DeliveriesDao deliveryDao;
	
	@MockBean
	private RestTemplate restTemplate;
	
	@SpyBean
	private DeliveryServiceImpl service;
	
	@Test
	@Order(1)
    void testCreateDelivery() throws Exception {
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
		
		OrderTO orderTO = new OrderTO();
		orderTO.setId(uuid);
		orderTO.setPaymentStatus("Completed");
		orderTO.setStatus("Completed");
		
		doReturn(Optional.of(orderTO)).when(service).fetchOrderById(Mockito.any());
		doReturn(mockDeliveryEntity).when(deliveryDao).save(Mockito.any());

        DeliveriesEntity result = service.createDelivery(mockDeliveryTO);

        assertNotNull(result);
        verify(service, times(1)).createDelivery(Mockito.any());
    }
	
	@Test
	@Order(2)
    void testGetDeliveryById() throws Exception {
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
		
		when(deliveryDao.findById(uuid)).thenReturn(Optional.of(mockDeliveryEntity));

		DeliveriesTO result = service.getDeliveryById(uuid);

        assertNotNull(result);
        verify(service, times(1)).getDeliveryById(Mockito.any());
    }
	
	@Test
	@Order(3)
    void testuUdateDeliveryStatus() throws Exception {
		UUID uuid = UUID.randomUUID();
        DeliveriesEntity mockDeliveryEntity = new DeliveriesEntity();
        mockDeliveryEntity.setCreatedAt(LocalDateTime.now());

        mockDeliveryEntity.setDeliveryPersonId(uuid);
        mockDeliveryEntity.setEstimatedTime(LocalDateTime.now());
        mockDeliveryEntity.setId(uuid);
        mockDeliveryEntity.setLocation("Delhi");
        mockDeliveryEntity.setOrderId(uuid);
        mockDeliveryEntity.setStatus("Pending");
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
		when(deliveryDao.findById(uuid)).thenReturn(Optional.of(mockDeliveryEntity));
		doReturn(mockDeliveryEntity).when(deliveryDao).save(Mockito.any());

		DeliveriesTO result = service.updateDeliveryStatus(uuid,"Pending");

        assertNotNull(result);
        verify(service, times(1)).updateDeliveryStatus(Mockito.any(),Mockito.anyString());
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
		
		when(deliveryDao.findByOrderId(uuid)).thenReturn(mockDeliveryEntity);

		DeliveriesTO result = service.getDeliveryByOrderId(uuid);

        assertNotNull(result);
        verify(service, times(1)).getDeliveryByOrderId(Mockito.any());
    }
}
