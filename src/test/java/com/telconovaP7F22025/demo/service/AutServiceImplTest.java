package com.telconovaP7F22025.demo.service;

import com.telconovaP7F22025.demo.dto.aut.LoginRequest;
import com.telconovaP7F22025.demo.dto.aut.RegisterRequest;
import com.telconovaP7F22025.demo.model.User;
import com.telconovaP7F22025.demo.repository.UserRepository;
import com.telconovaP7F22025.demo.service.impl.AutServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AutServiceImpl autService;

    @Test
    void authenticateUserSuccess() {
        LoginRequest req = new LoginRequest("test@mail.com", "1234");

        User user = new User();
        user.setEmailUsuario("test@mail.com");
        user.setPasswordUsuario("hashed");

        when(userRepository.findByEmailUsuario("test@mail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "hashed")).thenReturn(true);

        boolean result = autService.authenticateUser(req);

        assertTrue(result);
        verify(userRepository).findByEmailUsuario("test@mail.com");
        verify(passwordEncoder).matches("1234", "hashed");
    }

    @Test
    void authenticateUserFailsUserNotFound() {
        LoginRequest req = new LoginRequest("notfound@mail.com", "1234");

        when(userRepository.findByEmailUsuario("notfound@mail.com")).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> autService.authenticateUser(req));
        assertEquals("Invalid email or password", ex.getMessage());

        verify(userRepository).findByEmailUsuario("notfound@mail.com");
    }

    @Test
    void authenticateUserFailsWrongPassword() {
        LoginRequest req = new LoginRequest("test@mail.com", "wrong");

        User user = new User();
        user.setEmailUsuario("test@mail.com");
        user.setPasswordUsuario("hash");

        when(userRepository.findByEmailUsuario("test@mail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "hash")).thenReturn(false);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> autService.authenticateUser(req));
        assertEquals("Invalid email or password", ex.getMessage());
    }

    @Test
    void registerUserSuccess() {
        RegisterRequest req = new RegisterRequest("John", "john@mail.com", "1234", "ADMIN");

        when(userRepository.existsByEmailUsuario("john@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("hashed");

        boolean created = autService.registerUser(req);

        assertTrue(created);
        verify(userRepository).existsByEmailUsuario("john@mail.com");
        verify(passwordEncoder).encode("1234");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUserFailsBecauseExists() {
        RegisterRequest req = new RegisterRequest("John", "john@mail.com", "1234", "ADMIN");

        when(userRepository.existsByEmailUsuario("john@mail.com")).thenReturn(true);

        boolean created = autService.registerUser(req);

        assertFalse(created);
        verify(userRepository).existsByEmailUsuario("john@mail.com");
        verify(userRepository, never()).save(any());
    }
}
