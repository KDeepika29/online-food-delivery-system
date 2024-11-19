package com.fooddelivery.deliveryservice.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.fooddelivery.deliveryservice.entity.DeliveriesEntity;

public interface DeliveriesDao extends ListCrudRepository <DeliveriesEntity, Integer> {

	Optional<DeliveriesEntity> findById(UUID id);

	DeliveriesEntity findByOrderId(UUID orderId);

}
