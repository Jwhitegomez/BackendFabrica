package com.telconovaP7F22025.demo.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void test_GenerateAndValidateToken() {
        String token = jwtUtil.generateToken("test@example.com");

        assertNotNull(token);

        String extractedUser = jwtUtil.extractUsername(token);
        assertEquals("test@example.com", extractedUser);

        assertTrue(jwtUtil.validateToken(token, "test@example.com"));
    }
}

