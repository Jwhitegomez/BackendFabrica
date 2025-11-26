package com.telconovaP7F22025.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telconovaP7F22025.demo.config.JwtUtil;
import com.telconovaP7F22025.demo.dto.aut.LoginRequest;
import com.telconovaP7F22025.demo.dto.aut.RegisterRequest;
import com.telconovaP7F22025.demo.service.AutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AutController.class)
@AutoConfigureMockMvc(addFilters = false) // <-- desactiva filtros de seguridad para estos tests de controlador
class AutControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;

    @MockBean private AutService autService;
    @MockBean private JwtUtil jwtUtil;

    @Test
    void loginSuccess() throws Exception {
        LoginRequest req = new LoginRequest("user@mail.com", "pass");

        when(autService.authenticateUser(any())).thenReturn(true);
        when(jwtUtil.generateToken("user@mail.com")).thenReturn("jwt-token");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.message").value("Login exitoso"));
    }

    @Test
    void loginFails() throws Exception {
        LoginRequest req = new LoginRequest("u@mail.com", "pass");

        when(autService.authenticateUser(any())).thenReturn(false);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Credenciales inválidas"));
    }

    @Test
    void registerSuccess() throws Exception {
        RegisterRequest req = new RegisterRequest("John", "j@mail.com", "1234", "ADMIN");

        when(autService.registerUser(any())).thenReturn(true);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Usuario registrado exitosamente"));
    }

    @Test
    void registerFails() throws Exception {
        RegisterRequest req = new RegisterRequest("John", "j@mail.com", "1234", "ADMIN");

        when(autService.registerUser(any())).thenReturn(false);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isConflict())
                .andExpect(content().string("El usuario ya existe"));
    }
}
