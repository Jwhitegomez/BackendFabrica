package com.telconovaP7F22025.demo.repository;

import com.telconovaP7F22025.demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        // Limpia la tabla antes de cada prueba para evitar conflictos con el constraint UNIQUE
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Debe encontrar un usuario por su email")
    void testFindByEmailUsuario() {
        // Arrange
        User user = new User();
        user.setEmailUsuario("unique_user1@example.com");
        user.setPasswordUsuario("123456");
        user.setName("Test User");
        user.setRole("USER");

        userRepository
