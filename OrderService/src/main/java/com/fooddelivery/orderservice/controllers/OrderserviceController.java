package com.fooddelivery.orderservice.controllers;

import com.fooddelivery.orderservice.models.Order;
import com.fooddelivery.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderserviceController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable UUID id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable UUID id, @RequestBody String status) {
        return orderService.updateOrderStatus(id, status);
    }

    @GetMapping("/user/{user_id}")
    public List<Order> getOrdersByUserId(@PathVariable("user_id") UUID userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/restaurant/{restaurant_id}")
    public List<Order> getOrdersByRestaurantId(@PathVariable("restaurant_id") Long restaurantId) {
        return orderService.getOrdersByRestaurantId(restaurantId);
    }
}