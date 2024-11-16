package com.fooddelivery.orderservice.controllers;

import com.fooddelivery.orderservice.models.Order;
import com.fooddelivery.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable Long id, @RequestBody String status) {
        return orderService.updateOrderStatus(id, status);
    }

    @GetMapping("/user/{user_id}")
    public List<Order> getOrdersByUserId(@PathVariable("user_id") Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/restaurant/{restaurant_id}")
    public List<Order> getOrdersByRestaurantId(@PathVariable("restaurant_id") Long restaurantId) {
        return orderService.getOrdersByRestaurantId(restaurantId);
    }
}