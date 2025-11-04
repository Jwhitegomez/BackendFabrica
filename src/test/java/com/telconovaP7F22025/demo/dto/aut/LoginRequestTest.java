package com.telconovaP7F22025.demo.dto.aut;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    @Test
    void testLoginRequestValues() {
        // Crear el objeto record con valores de prueba
        LoginRequest request = new LoginRequest("user@example.com", "mypassword");

        // Verificar que los valores se asignen correctamente
        assertEquals("user@example.com", request.email());
        assertEquals("mypassword", request.password());
    }

    @Test
    void testLoginRequestEquality() {
        // Dos objetos con los mismos valores deben ser iguales
        LoginRequest r1 = new LoginRequest("a@b.com", "1234");
        LoginRequest r2 = new LoginRequest("a@b.com", "1234");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testLoginRequestToString() {
        LoginRequest request = new LoginRequest("test@domain.com", "pass");
        String text = request.toString();

        assertTrue(text.contains("test@domain.com"));
        assertTrue(text.contains("pass"));
    }
}
