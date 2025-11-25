package com.telconovaP7F22025.demo.controller;

import com.telconovaP7F22025.demo.config.JwtUtil;
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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutControllerTest {

    @Mock
    private AutService autService;

    @Mock
    private JwtUtil jwtUtil;

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
        when(jwtUtil.generateToken("user@test.com")).thenReturn("fake-token");

        ResponseEntity<?> response = autController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Map body = (Map) response.getBody();
        assertNotNull(body);
        assertEquals("fake-token", body.get("token"));
        assertEquals("Login exitoso", body.get("message"));

        verify(autService).authenticateUser(request);
    }

    @Test
    void testLoginFailed() {
        LoginRequest request = new LoginRequest("wrong@test.com", "badpass");
        when(autService.authenticateUser(request)).thenReturn(false);

        ResponseEntity<?> response = autController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenciales inválidas", response.getBody());

        verify(autService).authenticateUser(request);
    }

    @Test
    void testRegisterSuccessful() {
        RegisterRequest request = new RegisterRequest("new@test.com", "pass123", "New User", "USER");
        when(autService.registerUser(request)).thenReturn(true);

        ResponseEntity<String> response = autController.register(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario registrado exitosamente", response.getBody());

        verify(autService).registerUser(request);
    }

    @Test
    void testRegisterConflict() {
        RegisterRequest request = new RegisterRequest("existing@test.com", "pass123", "Existing", "USER");
        when(autService.registerUser(request)).thenReturn(false);

        ResponseEntity<String> response = autController.register(request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El usuario ya existe", response.getBody());

        verify(autService).registerUser(request);
    }
}
