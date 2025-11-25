package com.telconovaP7F22025.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telconovaP7F22025.demo.dto.aut.LoginRequest;
import com.telconovaP7F22025.demo.dto.order.CreateOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkOrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    public void setup() throws Exception {
        // 1. Obtener Token primero (Login con usuario del schema.sql)
        LoginRequest login = new LoginRequest("admin@telconova.com", "secret");
        
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        // Extraer token simple del JSON {"token":"...", ...}
        this.token = objectMapper.readTree(response).get("token").asText();
    }

    @Test
    public void shouldCreateOrderWithToken() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest(
            "Cliente Integration", "999888", "Soporte", "MEDIA", "Prueba auto"
        );

        mockMvc.perform(post("/api/orders")
                .header("Authorization", "Bearer " + token) // Enviamos Token
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nameCliente").value("Cliente Integration"));
    }
    
    @Test
    public void shouldFailWithoutToken() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest(
            "Intruso", "000", "Hack", "ALTA", "Sin token"
        );

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden()); // 403 Forbidden
    }
}