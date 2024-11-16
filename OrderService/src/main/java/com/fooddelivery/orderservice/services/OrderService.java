package com.fooddelivery.orderservice.services;

import com.fooddelivery.orderservice.models.Order;
import com.fooddelivery.orderservice.repositories.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        // logic to create order
        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(Long id) {
        // logic to get order by id
        return Optional.empty();
    }

    public Order updateOrderStatus(Long id, String status) {
        // logic to update order status
        return new Order();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        // logic to get orders by user id
        return List.of();
    }

    public List<Order> getOrdersByRestaurantId(Long restaurantId) {
        // logic to get orders by restaurant id
        return List.of();
    }
}