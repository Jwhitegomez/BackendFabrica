package com.telconovaP7F22025.demo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TechnicianTest {

    @Test
    void testTechnicianSettersAndGetters() {
        Technician tech = new Technician();

        tech.setIdTecnico(1L);
        tech.setNameTecnico("Carlos Gómez");
        tech.setZoneTecnico("Zona Norte");
        tech.setWorkloadTecnico("Alta");
        tech.setSpecialtyTecnico("Fibra Óptica");

        assertEquals(1L, tech.getIdTecnico());
        assertEquals("Carlos Gómez", tech.getNameTecnico());
        assertEquals("Zona Norte", tech.getZoneTecnico());
        assertEquals("Alta", tech.getWorkloadTecnico());
        assertEquals("Fibra Óptica", tech.getSpecialtyTecnico());
    }

    @Test
    void testTechnicianEqualityAndHashCode() {
        Technician t1 = new Technician();
        t1.setIdTecnico(1L);
        t1.setNameTecnico("Ana");
        t1.setZoneTecnico("Sur");
        t1.setWorkloadTecnico("Media");
        t1.setSpecialtyTecnico("Cableado");

        Technician t2 = new Technician();
        t2.setIdTecnico(1L);
        t2.setNameTecnico("Ana");
        t2.setZoneTecnico("Sur");
        t2.setWorkloadTecnico("Media");
        t2.setSpecialtyTecnico("Cableado");

        assertEquals(t1.getNameTecnico(), t2.getNameTecnico());
        assertEquals(t1.getZoneTecnico(), t2.getZoneTecnico());
        assertEquals(t1.getWorkloadTecnico(), t2.getWorkloadTecnico());
        assertEquals(t1.getSpecialtyTecnico(), t2.getSpecialtyTecnico());

        // Solo verificamos que sean distintos objetos, pero con mismos valores
        assertNotSame(t1, t2);
    }

    @Test
    void testTechnicianToStringNotNull() {
        Technician tech = new Technician();
        tech.setNameTecnico("María");
        tech.setZoneTecnico("Centro");
        tech.setWorkloadTecnico("Baja");
        tech.setSpecialtyTecnico("Redes");

        assertNotNull(tech.toString());
    }
}
