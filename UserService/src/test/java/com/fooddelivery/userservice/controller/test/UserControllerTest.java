package com.fooddelivery.userservice.controller.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fooddelivery.userservice.controller.UserController;
import com.fooddelivery.userservice.dto.User;
import com.fooddelivery.userservice.service.UserService;

public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testRegisterUser() throws Exception {
		User user = new User("testUser", "test@example.com", "hashedPassword", "1234567890");

		when(userService.registerUser(any(String.class), any(String.class), any(String.class), any(String.class)))
				.thenReturn("User registered successfully");

		mockMvc.perform(post("/user/register").contentType("application/json").content(
				"{\"username\":\"testUser\",\"email\":\"test@example.com\",\"passwordHash\":\"hashedPassword\",\"phone\":\"1234567890\"}"))
				.andExpect(status().isOk()).andExpect(content().string("User registered successfully"));
	}

	@Test
	public void testLoginUser() throws Exception {
		User user = new User("testUser", "test@example.com", "hashedPassword", "1234567890");

		when(userService.loginUser(any(String.class), any(String.class))).thenReturn("jwtToken");

		mockMvc.perform(post("/user/login").contentType("application/json")
				.content("{\"username\":\"testUser\",\"passwordHash\":\"hashedPassword\"}")).andExpect(status().isOk())
				.andExpect(content().string("jwtToken"));
	}

	@Test
	public void testGetUserProfile() throws Exception {
		User user = new User("testUser", "test@example.com", "hashedPassword", "1234567890");
		when(userService.getUserProfile("testUser")).thenReturn(user);

		mockMvc.perform(get("/user/profile").param("username", "testUser")).andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("testUser"))
				.andExpect(jsonPath("$.email").value("test@example.com"))
				.andExpect(jsonPath("$.phone").value("1234567890"));
	}

	@Test
	public void testUpdateUserProfile() throws Exception {
		User user = new User("testUser", "test@example.com", "newPasswordHash", "0987654321");

		when(userService.updateUserProfile(any(UUID.class), any(String.class), any(String.class)))
				.thenReturn("User profile updated successfully");

		mockMvc.perform(put("/user/update-profile").param("userId", "c9b8f99c-e405-48a4-9440-63e88b57c8ef") // example
																											// UUID
				.contentType("application/json")
				.content("{\"passwordHash\":\"newPasswordHash\",\"phone\":\"0987654321\"}")).andExpect(status().isOk())
				.andExpect(content().string("User profile updated successfully"));
	}
}