package com.fooddelivery.userservice.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.userservice.dto.User;
import com.fooddelivery.userservice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// Register a new user
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		return ResponseEntity.ok(
				userService.registerUser(user.getUsername(), user.getEmail(), user.getPasswordHash(), user.getPhone()));
	}

	// Login a user and return JWT token
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.loginUser(user.getUsername(), user.getPasswordHash()));
	}

	// Get the user's profile
	@GetMapping("/profile")
	public ResponseEntity<User> getProfile(@RequestParam String username) {
		return ResponseEntity.ok(userService.getUserProfile(username));
	}

	// Update the user's profile
	@PutMapping("/update-profile")
	public ResponseEntity<String> updateProfile(@RequestParam UUID userId, @RequestBody User user) {
		return ResponseEntity.ok(userService.updateUserProfile(userId, user.getPasswordHash(), user.getPhone()));
	}
}
