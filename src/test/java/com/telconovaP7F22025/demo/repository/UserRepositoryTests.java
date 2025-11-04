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
        // Limpia la tabla antes de cada prueba para evitar conflictos con la restricción UNIQUE
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

        userRepository.save(user);

        // Act
        Optional<User> found = userRepository.findByEmailUsuario("unique_user1@example.com");

        // Assert
        assertTrue(found.isPresent(), "El usuario debería existir en la base de datos");
        assertEquals("Test User", found.get().getName());
        assertEquals("USER", found.get().getRole());
    }

    @Test
    @DisplayName("Debe verificar correctamente si un email ya existe")
    void testExistsByEmailUsuario() {
        // Arrange
        User user = new User();
        user.setEmailUsuario("unique_user2@example.com");
        user.setPasswordUsuario("abcdef");
        user.setName("Exists User");
        user.setRole("ADMIN");

        userRepository.save(user);

        // Act & Assert
        assertTrue(userRepository.existsByEmailUsuario("unique_user2@example.com"),
                "El email debería existir en la base de datos");
        assertFalse(userRepository.existsByEmailUsuario("nope@example.com"),
                "El email no debería existir en la base de datos");
    }
}
