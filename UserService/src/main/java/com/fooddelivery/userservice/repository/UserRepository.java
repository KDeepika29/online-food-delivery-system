package com.fooddelivery.userservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fooddelivery.userservice.dto.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);
}
