package com.telconovaP7F22025.demo.repository;

import com.telconovaP7F22025.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void test_SaveAndFindUser() {
        User u = new User();
        u.setEmailUsuario("test@e.com");
        u.setPasswordUsuario("123");
        u.setName("John");
        u.setRole("USER");

        repository.save(u);

        assertTrue(repository.existsByEmailUsuario("test@e.com"));
        assertTrue(repository.findByEmailUsuario("test@e.com").isPresent());
    }
}

