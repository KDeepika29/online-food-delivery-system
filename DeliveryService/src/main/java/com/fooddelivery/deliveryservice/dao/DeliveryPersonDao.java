package com.fooddelivery.deliveryservice.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.fooddelivery.deliveryservice.entity.DeliveryPersonEntity;

public interface DeliveryPersonDao extends ListCrudRepository <DeliveryPersonEntity, Integer> {

	Optional<DeliveryPersonEntity> findById(UUID id);

}
