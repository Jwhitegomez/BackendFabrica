package com.telconovaP7F22025.demo.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void generateTokenAndExtractUsername() {
        String token = jwtUtil.generateToken("user@test.com");

        assertNotNull(token);
        assertEquals("user@test.com", jwtUtil.extractUsername(token));
    }

    @Test
    void validateTokenSuccess() {
        String token = jwtUtil.generateToken("user@test.com");

        assertTrue(jwtUtil.validateToken(token, "user@test.com"));
    }

    @Test
    void validateTokenWrongUserFails() {
        String token = jwtUtil.generateToken("user@test.com");

        assertFalse(jwtUtil.validateToken(token, "other@mail.com"));
    }
}

