package com.telconovaP7F22025.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtRequestFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        jwtRequestFilter = new JwtRequestFilter(jwtUtil);
        SecurityContextHolder.clearContext();
    }

    @Test
    void test_NoAuthorizationHeader() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void test_InvalidTokenFormat() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "InvalidHeader");

        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void test_ValidTokenAndUserAuthenticated() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer VALID_TOKEN");

        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtUtil.extractUsername("VALID_TOKEN")).thenReturn("test@example.com");
        when(jwtUtil.validateToken("VALID_TOKEN", "test@example.com")).thenReturn(true);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("test@example.com",
                SecurityContextHolder.getContext().getAuthentication().getName());

        verify(filterChain).doFilter(request, response);
    }
}
