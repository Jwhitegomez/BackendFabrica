package com.telconovaP7F22025.demo.service;

import com.telconovaP7F22025.demo.dto.aut.LoginRequest;
import com.telconovaP7F22025.demo.dto.aut.RegisterRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AutServiceTest {

    // Clase de prueba simple que implementa la interfaz
    static class DummyAutService implements AutService {
        @Override
        public boolean authenticateUser(LoginRequest loginRequest) {
            return loginRequest != null && "user@example.com".equals(loginRequest.getEmail()) && "1234".equals(loginRequest.getPassword());
        }

        @Override
        public boolean registerUser(RegisterRequest registerRequest) {
            return registerRequest != null && registerRequest.getEmail() != null && !registerRequest.getEmail().isEmpty();
        }
    }

    @Test
    void testAuthenticateUserSuccess() {
        DummyAutService service = new DummyAutService();
        LoginRequest request = new LoginRequest();
        request.setEmail("user@example.com");
        request.setPassword("1234");
        assertTrue(service.authenticateUser(request));
    }

    @Test
    void testAuthenticateUserFail() {
        DummyAutService service = new DummyAutService();
        LoginRequest request = new LoginRequest();
        request.setEmail("wrong@example.com");
        request.setPassword("bad");
        assertFalse(service.authenticateUser(request));
    }

    @Test
    void testRegisterUserSuccess() {
        DummyAutService service = new DummyAutService();
        RegisterRequest request = new RegisterRequest();
        request.setEmail("new@example.com");
        assertTrue(service.registerUser(request));
    }

    @Test
    void testRegisterUserFail() {
        DummyAutService service = new DummyAutService();
        RegisterRequest request = new RegisterRequest();
        request.setEmail("");
        assertFalse(service.registerUser(request));
    }
}
