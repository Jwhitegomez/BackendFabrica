package com.telconovaP7F22025.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtRequestFilterTest {

    @Mock private JwtUtil jwtUtil;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain filterChain;

    @InjectMocks private JwtRequestFilter filter;

    @BeforeEach
    void clearContext() {
        // Muy importante: limpiar el contexto entre tests para evitar autenticación residual
        SecurityContextHolder.clearContext();
    }

    @Test
    void validTokenAuthenticatesUser() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer abc123");
        when(jwtUtil.extractUsername("abc123")).thenReturn("user@test.com");
        when(jwtUtil.validateToken("abc123", "user@test.com")).thenReturn(true);

        filter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void invalidTokenDoesNotAuthenticate() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        // forzamos que extraer el username falle: así el filtro no autentica
        when(jwtUtil.extractUsername("token")).thenThrow(new RuntimeException("invalid"));
        // también aseguramos que validateToken no devuelva true por si acaso
        when(jwtUtil.validateToken(anyString(), anyString())).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void noTokenSkipsAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    // Opcional: aumenta cobertura cubriendo header mal formado
    @Test
    void malformedHeaderSkipsAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Token xyz");
        filter.doFilterInternal(request, response, filterChain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}
