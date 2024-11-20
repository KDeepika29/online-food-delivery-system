package com.fooddelivery.userservice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Bcrypt {
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public String getHashedPassword(String password) {
		// Hash the password
		return passwordEncoder.encode(password);
	}

	public boolean checkPassword(String password, String hashedPassword) {
		// Check if password matches hashed password
		return passwordEncoder.matches(password, hashedPassword);
	}
}