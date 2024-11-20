package com.fooddelivery.userservice.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.userservice.dto.User;
import com.fooddelivery.userservice.repository.UserRepository;
import com.fooddelivery.userservice.util.JwtUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;
	
	private final PasswordEncoder passwordEncoder;

	@Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

	// Register a new user
	@Transactional
	public String registerUser(String username, String email, String password, String phone) {
		if (userRepository.findByUsername(username).isPresent()) {
			throw new RuntimeException("Username already exists");
		}

		if (userRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Email already exists");
		}

		User newUser = new User();
		newUser.setUsername(username);
		newUser.setEmail(email);
		newUser.setPasswordHash(passwordEncoder.encode(password)); // Encoding password
		newUser.setPhone(phone);

		userRepository.save(newUser);

		return "User registered successfully!";
	}

	// Login a user and return JWT token
	public String loginUser(String username, String password) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Invalid username or password"));

		if (!passwordEncoder.matches(password, user.getPasswordHash())) {
			throw new RuntimeException("Invalid username or password");
		}

		// Generate JWT token
		return jwtUtil.generateToken(user.getUsername());
	}

	// Get user details by username (for internal use)
	public User getUserDetails(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
	}

	// Update user profile
	public String updateUserProfile(UUID userId, String newPassword, String newPhone) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		user.setPasswordHash(passwordEncoder.encode(newPassword)); // Encoding new password
		user.setPhone(newPhone);
		user.setUpdatedAt(LocalDateTime.now());
		userRepository.save(user);

		return "User profile updated successfully!";
	}

}
