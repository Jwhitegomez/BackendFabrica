package com.telconovaP7F22025.demo.controller;

import com.telconovaP7F22025.demo.config.JwtUtil;
import com.telconovaP7F22025.demo.dto.aut.LoginRequest;
import com.telconovaP7F22025.demo.dto.aut.RegisterRequest;
import com.telconovaP7F22025.demo.service.AutService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutControllerTest {

    @Test
    void test_LoginSuccess() {
        AutService service = mock(AutService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        AutController controller = new AutController(service, jwtUtil);

        LoginRequest req = new LoginRequest("a@a.com", "123");

        when(service.authenticateUser(req)).thenReturn(true);
        when(jwtUtil.generateToken("a@a.com")).thenReturn("TOKEN123");

        ResponseEntity<?> response = controller.login(req);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("TOKEN123"));
    }

    @Test
    void test_LoginUnauthorized() {
        AutService service = mock(AutService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        AutController controller = new AutController(service, jwtUtil);

        LoginRequest req = new LoginRequest("a@a.com", "123");

        when(service.authenticateUser(req)).thenReturn(false);

        ResponseEntity<?> response = controller.login(req);

        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    void test_RegisterSuccess() {
        AutService service = mock(AutService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        AutController controller = new AutController(service, jwtUtil);

        RegisterRequest req = new RegisterRequest("a@a.com", "123", "John", "USER");

        when(service.registerUser(req)).thenReturn(true);

        ResponseEntity<String> resp = controller.register(req);

        assertEquals(201, resp.getStatusCodeValue());
    }

    @Test
    void test_RegisterConflict() {
        AutService service = mock(AutService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        AutController controller = new AutController(service, jwtUtil);

        RegisterRequest req = new RegisterRequest("a@a.com", "123", "John", "USER");

        when(service.registerUser(req)).thenReturn(false);

        ResponseEntity<String> resp = controller.register(req);

        assertEquals(409, resp.getStatusCodeValue());
    }
}
