package com.telconovaP7F22025.demo.repository;

import com.telconovaP7F22025.demo.model.Technician;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TechnicianRepositoryTests {

    @Autowired
    private TechnicianRepository technicianRepository;

    @Test
    void testFindByNameTecnico() {
        Technician tech = new Technician();
        tech.setNameTecnico("Carlos Lopez");
        tech.setZoneTecnico("Zona Norte");
        tech.setWorkloadTecnico("Alta");
        tech.setSpecialtyTecnico("Fibra Óptica");

        technicianRepository.save(tech);

        Optional<Technician> found = technicianRepository.findByNameTecnico("Carlos Lopez");
        assertTrue(found.isPresent());
        assertEquals("Fibra Óptica", found.get().getSpecialtyTecnico());
    }

    @Test
    void testExistsByNameTecnico() {
        Technician tech = new Technician();
        tech.setNameTecnico("Ana Ruiz");
        tech.setZoneTecnico("Zona Sur");
        tech.setWorkloadTecnico("Media");
        tech.setSpecialtyTecnico("Redes");

        technicianRepository.save(tech);

        assertTrue(technicianRepository.existsByNameTecnico("Ana Ruiz"));
        assertFalse(technicianRepository.existsByNameTecnico("Pedro Pérez"));
    }
}
