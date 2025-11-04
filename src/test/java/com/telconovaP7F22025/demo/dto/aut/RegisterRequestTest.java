package com.telconovaP7F22025.demo.dto.aut;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    @Test
    void testRegisterRequestValues() {
        // Crear el objeto con valores de prueba
        RegisterRequest request = new RegisterRequest("user@example.com", "12345", "Test User", "ADMIN");

        // Verificar que los valores se asignen correctamente
        assertEquals("user@example.com", request.email());
        assertEquals("12345", request.password());
        assertEquals("Test User", request.name());
        assertEquals("ADMIN", request.role());
    }

    @Test
    void testRegisterRequestEquality() {
        RegisterRequest r1 = new RegisterRequest("a@b.com", "pass", "User1", "USER");
        RegisterRequest r2 = new RegisterRequest("a@b.com", "pass", "User1", "USER");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testRegisterRequestToString() {
        RegisterRequest req = new RegisterRequest("t@e.com", "pwd", "Tester", "TECH");
        String result = req.toString();

        assertTrue(result.contains("t@e.com"));
        assertTrue(result.contains("Tester"));
        assertTrue(result.contains("TECH"));
    }
}
