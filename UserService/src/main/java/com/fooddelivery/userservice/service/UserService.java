package com.fooddelivery.userservice.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.userservice.dto.User;
import com.fooddelivery.userservice.dto.UserTypes;
import com.fooddelivery.userservice.repository.UserRepository;
import com.fooddelivery.userservice.util.Bcrypt;
import com.fooddelivery.userservice.util.JwtUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Bcrypt bcrypt;

	@Autowired
	private JwtUtil jwtUtil;

	public String registerUser(String username, String email, String password, String phone) {
		if (userRepository.findByUsername(username).isPresent()) {
			throw new RuntimeException("Username already exists");
		}
		if (userRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Email already exists");
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPasswordHash(bcrypt.getHashedPassword(password));
		user.setPhone(phone);

		userRepository.save(user);
		return "User registered successfully!";
	}

	public String loginUser(String username, String password) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Invalid username or password"));

		if (!bcrypt.checkPassword(password, user.getPasswordHash())) {
			throw new RuntimeException("Invalid username or password");
		}

		return jwtUtil.generateToken(user.getUsername(), UserTypes.USER);
	}

	public User getUserProfile(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
	}

	public String updateUserProfile(UUID id, String newPassword, String newPhone) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

		if (newPassword != null) {
			user.setPasswordHash(bcrypt.getHashedPassword(newPassword));
		}
		if (newPhone != null) {
			user.setPhone(newPhone);
		}

		userRepository.save(user);
		return "User profile updated successfully!";
	}

	public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }
}
