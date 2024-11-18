package com.fooddelivery.deliveryservice.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddelivery.deliveryservice.dao.DeliveryPersonDao;
import com.fooddelivery.deliveryservice.entity.DeliveryPersonEntity;
import com.fooddelivery.deliveryservice.to.DeliveryPersonTO;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters=false)
@WebMvcTest(DeliveryPersonServiceImpl.class)
public class DeliveryPersonImplTest {
	
	@Autowired
	protected MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private DeliveryPersonDao deliveryDao;
	
	@SpyBean
	private DeliveryPersonServiceImpl service;
	
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
		
		DeliveryPersonEntity personEntity = new DeliveryPersonEntity();
		personEntity.setCreatedAt(personTO.getCreatedAt());
		personEntity.setId(personTO.getId());
		personEntity.setName(personTO.getName());
		personEntity.setPhone(personTO.getPhone());
		personEntity.setStatus(personTO.getStatus());
		personEntity.setUpdatedAt(personTO.getUpdatedAt());
		when(deliveryDao.findAll()).thenReturn(List.of(personEntity));

		List<DeliveryPersonTO> result = service.getAllDeliveryPersons();

        assertNotNull(result);
        verify(service, times(1)).getAllDeliveryPersons();
    }
	
	@Test
	@Order(2)
    void testUpdateDeliveryPersonStatus() throws Exception {
		UUID uuid = UUID.randomUUID();
		
		DeliveryPersonTO personTO = new DeliveryPersonTO();
		personTO.setCreatedAt(LocalDateTime.now());
		personTO.setId(uuid);
		personTO.setName("Akshara");
		personTO.setPhone("123456789");
		personTO.setStatus("Available");
		personTO.setUpdatedAt(LocalDateTime.now());
		
		DeliveryPersonEntity personEntity = new DeliveryPersonEntity();
		personEntity.setCreatedAt(personTO.getCreatedAt());
		personEntity.setId(personTO.getId());
		personEntity.setName(personTO.getName());
		personEntity.setPhone(personTO.getPhone());
		personEntity.setStatus(personTO.getStatus());
		personEntity.setUpdatedAt(personTO.getUpdatedAt());
		when(deliveryDao.findById(uuid)).thenReturn(Optional.of(personEntity));
		doReturn(personEntity).when(deliveryDao).save(Mockito.any());

		DeliveryPersonTO result = service.updateDeliveryPersonStatus(uuid,"Available");

        assertNotNull(result);
        verify(service, times(1)).updateDeliveryPersonStatus(Mockito.any(),Mockito.anyString());
    }
	
	
	

}
