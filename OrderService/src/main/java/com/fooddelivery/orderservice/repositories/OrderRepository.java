package com.fooddelivery.orderservice.repositories;

import com.fooddelivery.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);
    List<Order> findByRestaurantId(Long restaurantId);
}