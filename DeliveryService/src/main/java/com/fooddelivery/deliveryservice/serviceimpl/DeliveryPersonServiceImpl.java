package com.fooddelivery.deliveryservice.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fooddelivery.deliveryservice.constant.DeliveryConstant;
import com.fooddelivery.deliveryservice.constant.DeliveryConstant.Delivery_Person_Status;
import com.fooddelivery.deliveryservice.dao.DeliveryPersonDao;
import com.fooddelivery.deliveryservice.entity.DeliveryPersonEntity;
import com.fooddelivery.deliveryservice.service.DeliveryPersonService;
import com.fooddelivery.deliveryservice.to.DeliveryPersonTO;

@Service
public class DeliveryPersonServiceImpl implements DeliveryPersonService {

	@Autowired
	DeliveryPersonDao deliveryPersonDao;

	@Override
	public List<DeliveryPersonTO> getAllDeliveryPersons() {
		return deliveryPersonDao.findAll().stream().map((deliveryEntity) -> {
			DeliveryPersonTO deliveryTO = new DeliveryPersonTO();
			BeanUtils.copyProperties(deliveryEntity, deliveryTO);
			return deliveryTO;
		}).toList();
	}

	@Override
	public DeliveryPersonTO updateDeliveryPersonStatus(UUID id, String status) throws Exception {
//		System.out.print(Delivery_Person_Status.values().toString());
		 Delivery_Person_Status.validateStatus(status);
		

		Optional<DeliveryPersonEntity> deliveryPerson = deliveryPersonDao.findById(id);

		DeliveryPersonTO deliveryTO = new DeliveryPersonTO();
		// Update the status
		DeliveryPersonEntity deliveryEntity = new DeliveryPersonEntity();
		
		deliveryTO.setId(deliveryPerson.get().getId());
		deliveryTO.setName(deliveryPerson.get().getName());
		deliveryTO.setPhone(deliveryPerson.get().getPhone());
		deliveryTO.setCreatedAt(deliveryPerson.get().getCreatedAt());
		deliveryTO.setUpdatedAt(LocalDateTime.now());
		deliveryTO.setStatus(status);
		
		deliveryEntity.setId(deliveryTO.getId());
		deliveryEntity.setName(deliveryTO.getName());
		deliveryEntity.setPhone(deliveryTO.getPhone());
		deliveryEntity.setCreatedAt(deliveryTO.getCreatedAt());
		deliveryEntity.setUpdatedAt(deliveryTO.getUpdatedAt());
		deliveryEntity.setStatus(deliveryTO.getStatus());

		// Save and return the updated delivery person
		deliveryPersonDao.save(deliveryEntity);
		return deliveryTO;
	}

}
