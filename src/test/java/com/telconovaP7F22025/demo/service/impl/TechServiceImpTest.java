package com.telconovaP7F22025.demo.service.impl;

import com.telconovaP7F22025.demo.dto.tech.CreateRequest;
import com.telconovaP7F22025.demo.model.Technician;
import com.telconovaP7F22025.demo.repository.TechnicianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TechServiceImplTest {

    private TechnicianRepository repository;
    private TechServiceImpl service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(TechnicianRepository.class);
        service = new TechServiceImpl(repository);
    }

    @Test
    void createTechnician_shouldReturnFalse_whenNameExists() {
        CreateRequest req = new CreateRequest("Juan", "Zona1", "3", "Fibra");

        when(repository.existsByNameTecnico("Juan")).thenReturn(true);

        boolean result = service.createTechnician(req);

        assertFalse(result);
        verify(repository, never()).save(any());
    }

    @Test
    void createTechnician_shouldSaveAndReturnTrue_whenNameNotExists() {
        CreateRequest req = new CreateRequest("Ana", "Zona2", "1", "Cobre");

        when(repository.existsByNameTecnico("Ana")).thenReturn(false);

        boolean result = service.createTechnician(req);

        assertTrue(result);
        verify(repository, times(1)).save(any(Technician.class));
    }

    @Test
    void getAllTechnicians_shouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(new Technician(), new Technician()));

        List<Technician> result = service.getAllTechnicians();

        assertEquals(2, result.size());
    }
}
