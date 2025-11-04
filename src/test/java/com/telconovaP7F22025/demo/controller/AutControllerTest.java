package com.telconovaP7F22025.demo.controller;

import com.telconovaP7F22025.demo.dto.aut.LoginRequest;
import com.telconovaP7F22025.demo.dto.aut.RegisterRequest;
import com.telconovaP7F22025.demo.service.AutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutControllerTest {

    @Mock
    private AutService autService;

    @InjectMocks
    private AutController autController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccessful() {
        LoginRequest request = new LoginRequest("user@test.com", "password123");
        when(autService.authenticateUser(request)).thenReturn(true);

        ResponseEntity<String> response = autController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful", response.getBody());
        verify(autService).authenticateUser(request);
    }

    @Test
    void testLoginFailed() {
        LoginRequest request = new LoginRequest("wrong@test.com", "badpass");
        when(autService.authenticateUser(request)).thenReturn(false);

        ResponseEntity<String> response = autController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid email or password", response.getBody());
        verify(autService).authenticateUser(request);
    }

    @Test
    void testRegisterSuccessful() {
        RegisterRequest request = new RegisterRequest("new@test.com", "pass123", "New User", "USER");
        when(autService.registerUser(request)).thenReturn(true);

        ResponseEntity<String> response = autController.register(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User registered", response.getBody());
        verify(autService).registerUser(request);
    }

    @Test
    void testRegisterConflict() {
        RegisterRequest request = new RegisterRequest("existing@test.com", "pass123", "Existing", "USER");
        when(autService.registerUser(request)).thenReturn(false);

        ResponseEntity<String> response = autController.register(request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User with that email already exists", response.getBody());
        verify(autService).registerUser(request);
    }
}
