package com.fooddelivery.userservice.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fooddelivery.userservice.dto.User;
import com.fooddelivery.userservice.dto.UserTypes;
import com.fooddelivery.userservice.repository.UserRepository;
import com.fooddelivery.userservice.service.UserService;
import com.fooddelivery.userservice.util.Bcrypt;
import com.fooddelivery.userservice.util.JwtUtil;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Bcrypt bcrypt;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 

    }

    
    @Test
    public void testRegisterUser_Success() {
      
        String username = "testUser";
        String email = "test@example.com";
        String password = "password123";
        String phone = "1234567890";
        
        User existingUser = new User();
        existingUser.setUsername(username);
        existingUser.setEmail(email);
        
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(bcrypt.getHashedPassword(password)).thenReturn("hashedPassword");

  
        String result = userService.registerUser(username, email, password, phone);

      
        assertEquals("User registered successfully!", result);
        verify(userRepository, times(1)).save(any(User.class)); // Check if save was called once
    }

    @Test
    public void testRegisterUser_UsernameAlreadyExists() {
     
        String username = "existingUser";
        String email = "test@example.com";
        String password = "password123";
        String phone = "1234567890";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

       
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(username, email, password, phone);
        });

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    public void testLoginUser_Success() {
       
        String username = "testUser";
        String password = "password123";
        String hashedPassword = "hashedPassword";
        String token = "jwtToken";

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(hashedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(bcrypt.checkPassword(password, hashedPassword)).thenReturn(true);
        when(jwtUtil.generateToken(username, UserTypes.USER)).thenReturn(token);

      
        String result = userService.loginUser(username, password);

      
        assertEquals(token, result);
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
       
        String username = "testUser";
        String password = "wrongPassword";

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

       
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.loginUser(username, password);
        });

        assertEquals("Invalid username or password", exception.getMessage());
    }

    
    @Test
    public void testGetUserProfile_Success() {
     
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = userService.getUserProfile(username);

     
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    public void testGetUserProfile_UserNotFound() {
        
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserProfile(username);
        });

        assertEquals("User not found", exception.getMessage());
    }

   
    @Test
    public void testUpdateUserProfile_Success() {
        
        UUID userId = UUID.randomUUID();
        String newPassword = "newPassword123";
        String newPhone = "9876543210";
        
        User user = new User();
        user.setId(userId);
        user.setUsername("testUser");
        user.setPhone("1234567890");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bcrypt.getHashedPassword(newPassword)).thenReturn("hashedNewPassword");

        
        String result = userService.updateUserProfile(userId, newPassword, newPhone);

        assertEquals("User profile updated successfully!", result);
        verify(userRepository, times(1)).save(user); // Check if save was called once
        assertEquals(newPhone, user.getPhone());
    }

    @Test
    public void testUpdateUserProfile_UserNotFound() {
       
        UUID userId = UUID.randomUUID();
        String newPassword = "newPassword123";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

       
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUserProfile(userId, newPassword, null);
        });

        assertEquals("User not found", exception.getMessage());
    }
}