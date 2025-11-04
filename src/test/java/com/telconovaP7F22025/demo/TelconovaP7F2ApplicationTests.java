package com.telconovaP7F22025.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.telconovaP7F22025.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TelconovaP7F2ApplicationTests {

	@Test
	void contextLoads() {
	}

}



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
        assertTrue(found.isPresent());
        assertEquals("Test User", found.get().getName());
    }

    @Test
    void testExistsByEmailUsuario() {
        User user = new User();
       .setEmailUsuario("exists@example.com");
        user.setPasswordUsuario("abcdef");
        user.setName("Exists User");
        user.setRole("ADMIN");

        userRepository.save(user);

        boolean exists = userRepository.existsByEmailUsuario("exists@example.com");
        assertTrue(exists);

        boolean notExists = userRepository.existsByEmailUsuario("nope@example.com");
        assertFalse(notExists);
    }
}
