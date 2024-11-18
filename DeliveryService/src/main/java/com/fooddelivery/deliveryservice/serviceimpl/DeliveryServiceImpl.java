package com.fooddelivery.deliveryservice.serviceimpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.deliveryservice.constant.DeliveryConstant.Delivery_Person_Status;
import com.fooddelivery.deliveryservice.constant.DeliveryConstant.Status;
import com.fooddelivery.deliveryservice.dao.DeliveriesDao;
import com.fooddelivery.deliveryservice.entity.DeliveriesEntity;
import com.fooddelivery.deliveryservice.entity.DeliveryPersonEntity;
import com.fooddelivery.deliveryservice.service.DeliveryService;
import com.fooddelivery.deliveryservice.to.DeliveriesTO;
import com.fooddelivery.deliveryservice.to.DeliveryPersonTO;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	DeliveriesDao deliveriesDao;

	@Override
	public DeliveriesEntity createDelivery(DeliveriesTO deliveryTO) {

		DeliveriesEntity deliveryEntity = new DeliveriesEntity();
		deliveryEntity.setId(deliveryTO.getId());
		deliveryEntity.setOrderId(deliveryTO.getOrderId());
		deliveryEntity.setDeliveryPersonId(deliveryTO.getDeliveryPersonId());
		deliveryEntity.setStatus(deliveryTO.getStatus());
		deliveryEntity.setEstimatedTime(deliveryTO.getEstimatedTime());
		deliveryEntity.setLocation(deliveryTO.getLocation());

		return deliveriesDao.save(deliveryEntity);

	}

	@Override
	public DeliveriesTO getDeliveryById(UUID id) {
		// TODO Auto-generated method stub
		Optional<DeliveriesEntity> deliveryEntity = deliveriesDao.findById(id);
		DeliveriesTO deliveryTO = new DeliveriesTO();
		deliveryTO.setCreatedAt(deliveryEntity.get().getCreatedAt());
		deliveryTO.setDeliveryPersonId(deliveryEntity.get().getDeliveryPersonId());
		deliveryTO.setEstimatedTime(deliveryEntity.get().getEstimatedTime());
		deliveryTO.setId(deliveryEntity.get().getId());
		deliveryTO.setLocation(deliveryEntity.get().getLocation());
		deliveryTO.setOrderId(deliveryEntity.get().getOrderId());
		deliveryTO.setStatus(deliveryEntity.get().getStatus());
		deliveryTO.setUpdatedAt(deliveryEntity.get().getUpdatedAt());
		return deliveryTO;

	}

	@Override
	public DeliveriesTO updateDeliveryStatus(UUID id, String status) {
		
		Status.validateStatus(status);
		Optional<DeliveriesEntity> delivery = deliveriesDao.findById(id);

		DeliveriesTO deliveryTO = new DeliveriesTO();
		// Update the status
		DeliveriesEntity deliveryEntity = new DeliveriesEntity();
		
		deliveryTO.setId(delivery.get().getId());
		deliveryTO.setCreatedAt(delivery.get().getCreatedAt());
		deliveryTO.setDeliveryPersonId(delivery.get().getDeliveryPersonId());
		deliveryTO.setLocation(delivery.get().getLocation());
		deliveryTO.setEstimatedTime(delivery.get().getEstimatedTime());
		deliveryTO.setOrderId(delivery.get().getOrderId());
		deliveryTO.setUpdatedAt(LocalDateTime.now());
		deliveryTO.setStatus(status);
		
		deliveryEntity.setId(deliveryTO.getId());
		deliveryEntity.setCreatedAt(deliveryTO.getCreatedAt());
		deliveryEntity.setDeliveryPersonId(deliveryTO.getDeliveryPersonId());
		deliveryEntity.setStatus(deliveryTO.getStatus());
		deliveryEntity.setEstimatedTime(deliveryTO.getEstimatedTime());
		deliveryEntity.setLocation(deliveryTO.getLocation());
		deliveryEntity.setUpdatedAt(deliveryTO.getUpdatedAt());
		deliveryEntity.setOrderId(deliveryTO.getOrderId());

		// Save and return the updated delivery person
		deliveriesDao.save(deliveryEntity);
		return deliveryTO;
	}

	@Override
	public DeliveriesTO getDeliveryByOrderId(UUID orderId) {
		// TODO Auto-generated method stub
		DeliveriesTO deliveryTO = new DeliveriesTO();
		DeliveriesEntity delivery = deliveriesDao.findByOrderId(orderId);
		deliveryTO.setCreatedAt(delivery.getCreatedAt());
		deliveryTO.setDeliveryPersonId(delivery.getDeliveryPersonId());
		deliveryTO.setEstimatedTime(delivery.getEstimatedTime());
		deliveryTO.setId(delivery.getId());
		deliveryTO.setLocation(delivery.getLocation());
		deliveryTO.setOrderId(delivery.getOrderId());
		deliveryTO.setStatus(delivery.getStatus());
		deliveryTO.setUpdatedAt(delivery.getUpdatedAt());
		return deliveryTO;
	}

}
