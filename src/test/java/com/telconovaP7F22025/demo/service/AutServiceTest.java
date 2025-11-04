package com.telconovaP7F22025.demo.service;

import com.telconovaP7F22025.demo.dto.aut.LoginRequest;
import com.telconovaP7F22025.demo.dto.aut.RegisterRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AutServiceTest {

    static class DummyAutService implements AutService {
        @Override
        public boolean authenticateUser(LoginRequest loginRequest) {
            return loginRequest != null &&
                    "user@example.com".equals(loginRequest.email()) &&
                    "1234".equals(loginRequest.password());
        }

        @Override
        public boolean registerUser(RegisterRequest registerRequest) {
            return registerRequest != null &&
                    registerRequest.email() != null &&
                    !registerRequest.email().isEmpty();
        }
    }

    @Test
    void testAuthenticateUserSuccess() {
        DummyAutService service = new DummyAutService();
        LoginRequest request = new LoginRequest("user@example.com", "1234");
        assertTrue(service.authenticateUser(request));
    }

    @Test
    void testAuthenticateUserFail() {
        DummyAutService service = new DummyAutService();
        LoginRequest request = new LoginRequest("wrong@example.com", "bad");
        assertFalse(service.authenticateUser(request));
    }

    @Test
    void testRegisterUserSuccess() {
        DummyAutService service = new DummyAutService();
        RegisterRequest request = new RegisterRequest("new@example.com", "1234", "John", "USER");
        assertTrue(service.registerUser(request));
    }

    @Test
    void testRegisterUserFail() {
        DummyAutService service = new DummyAutService();
        RegisterRequest request = new RegisterRequest("", "1234", "John", "USER");
        assertFalse(service.registerUser(request));
    }
}
