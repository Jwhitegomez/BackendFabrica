package com.telconovaP7F22025.demo.dto.tech;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateRequestTest {

    @Test
    void testCreateRequestValues() {
        // Crear objeto con valores de ejemplo
        CreateRequest request = new CreateRequest("Juan Pérez", "Zona Norte", "Alta", "Fibra Óptica");

        // Verificar que los valores sean correctos
        assertEquals("Juan Pérez", request.nameTecnico());
        assertEquals("Zona Norte", request.zoneTecnico());
        assertEquals("Alta", request.workloadTecnico());
        assertEquals("Fibra Óptica", request.specialtyTecnico());
    }

    @Test
    void testCreateRequestEquality() {
        CreateRequest r1 = new CreateRequest("Ana", "Sur", "Media", "Cableado");
        CreateRequest r2 = new CreateRequest("Ana", "Sur", "Media", "Cableado");

        // Dos records con mismos valores deben ser iguales
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testCreateRequestToString() {
        CreateRequest req = new CreateRequest("Pedro", "Centro", "Baja", "Redes");
        String text = req.toString();

        assertTrue(text.contains("Pedro"));
        assertTrue(text.contains("Centro"));
        assertTrue(text.contains("Baja"));
        assertTrue(text.contains("Redes"));
    }
}
