package com.telconovaP7F22025.demo.repository;

import com.telconovaP7F22025.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// Esta anotación levanta un entorno JPA de prueba con base de datos H2 en memoria
@DataJpaTest
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmailUsuario() {
        User user = new User();
        user.setEmailUsuario("test@example.com");
        user.setPasswordUsuario("123456");
        user.setName("Test User");
        user.setRole("USER");

        userRepository.save(user);

        Optional<User> found = userRepository.findByEmailUsuario("test@example.com");

        assertTrue(found.isPresent(), "El usuario debería existir en la base de datos");
        assertEquals("Test User", found.get().getName());
    }

    @Test
    void testExistsByEmailUsuario() {
        User user = new User();
        user.setEmailUsuario("exists@example.com");
        user.setPasswordUsuario("abcdef");
        user.setName("Exists User");
        user.setRole("ADMIN");

        userRepository.save(user);

        boolean exists = userRepository.existsByEmailUsuario("exists@example.com");
        assertTrue(exists, "El usuario debería existir");

        boolean notExists = userRepository.existsByEmailUsuario("nope@example.com");
        assertFalse(notExists, "El usuario no debería existir");
    }
}
