package com.telconovaP7F22025.demo.service.impl;

import com.telconovaP7F22025.demo.dto.aut.LoginRequest;
import com.telconovaP7F22025.demo.dto.aut.RegisterRequest;
import com.telconovaP7F22025.demo.model.User;
import com.telconovaP7F22025.demo.repository.UserRepository;
import com.telconovaP7F22025.demo.service.impl.AutServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutServiceImplTest {

    @Test
    void test_AuthenticateUser_InvalidEmail() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);

        AutServiceImpl service = new AutServiceImpl(repo, encoder);
        LoginRequest req = new LoginRequest("a@a.com", "123");

        when(repo.findByEmailUsuario("a@a.com")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.authenticateUser(req));
    }

    @Test
    void test_AuthenticateUser_InvalidPassword() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);

        AutServiceImpl service = new AutServiceImpl(repo, encoder);
        LoginRequest req = new LoginRequest("a@a.com", "123");

        User u = new User();
        u.setEmailUsuario("a@a.com");
        u.setPasswordUsuario("ENCODED");

        when(repo.findByEmailUsuario("a@a.com")).thenReturn(Optional.of(u));
        when(encoder.matches("123", "ENCODED")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.authenticateUser(req));
    }

    @Test
    void test_AuthenticateUser_Success() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);

        AutServiceImpl service = new AutServiceImpl(repo, encoder);
        LoginRequest req = new LoginRequest("a@a.com", "123");

        User u = new User();
        u.setEmailUsuario("a@a.com");
        u.setPasswordUsuario("ENCODED");

        when(repo.findByEmailUsuario("a@a.com")).thenReturn(Optional.of(u));
        when(encoder.matches("123", "ENCODED")).thenReturn(true);

        assertTrue(service.authenticateUser(req));
    }

    @Test
    void test_RegisterUser_AlreadyExists() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);

        AutServiceImpl service = new AutServiceImpl(repo, encoder);

        RegisterRequest req = new RegisterRequest("a@a.com", "123", "John", "USER");

        when(repo.existsByEmailUsuario("a@a.com")).thenReturn(true);

        assertFalse(service.registerUser(req));
    }

    @Test
    void test_RegisterUser_Success() {
        UserRepository repo = mock(UserRepository.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);

        AutServiceImpl service = new AutServiceImpl(repo, encoder);

        RegisterRequest req = new RegisterRequest("a@a.com", "123", "John", "USER");

        when(repo.existsByEmailUsuario("a@a.com")).thenReturn(false);
        when(encoder.encode("123")).thenReturn("ENCODED");

        boolean result = service.registerUser(req);

        assertTrue(result);
        verify(repo).save(any(User.class));
    }
}
