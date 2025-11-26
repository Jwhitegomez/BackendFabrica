package com.telconovaP7F22025.demo.service.impl;

import com.telconovaP7F22025.demo.dto.order.CreateOrderRequest;
import com.telconovaP7F22025.demo.model.Technician;
import com.telconovaP7F22025.demo.model.WorkOrder;
import com.telconovaP7F22025.demo.repository.TechnicianRepository;
import com.telconovaP7F22025.demo.repository.WorkOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkOrderServiceImplTest {

    private WorkOrderRepository workRepo;
    private TechnicianRepository techRepo;
    private WorkOrderServiceImpl service;

    @BeforeEach
    void setup() {
        workRepo = mock(WorkOrderRepository.class);
        techRepo = mock(TechnicianRepository.class);
        service = new WorkOrderServiceImpl(workRepo, techRepo);
    }

    @Test
    void createWorkOrder_shouldSaveWithDefaultStatus() {
        CreateOrderRequest req = new CreateOrderRequest("Luis", "123", "Internet", "Alta", "Falla total");

        when(workRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        WorkOrder result = service.createWorkOrder(req);

        assertEquals("Luis", result.getNameCliente());
        assertEquals("PENDIENTE", result.getEstado());
        verify(workRepo, times(1)).save(any());
    }

    @Test
    void getAllOrders_shouldReturnList() {
        when(workRepo.findAll()).thenReturn(List.of(new WorkOrder(), new WorkOrder()));

        List<WorkOrder> list = service.getAllOrders();
        assertEquals(2, list.size());
    }

    @Test
    void getOrderById_shouldReturnOptional() {
        WorkOrder wo = new WorkOrder();
        wo.setIdOrder(1L);

        when(workRepo.findById(1L)).thenReturn(Optional.of(wo));

        Optional<WorkOrder> result = service.getOrderById(1L);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getIdOrder());
    }

    @Test
    void assignTechnician_shouldAssignAndSave() {
        WorkOrder wo = new WorkOrder();
        wo.setIdOrder(10L);

        Technician tech = new Technician();
        tech.setIdTecnico(5L);

        when(workRepo.findById(10L)).thenReturn(Optional.of(wo));
        when(techRepo.findById(5L)).thenReturn(Optional.of(tech));
        when(workRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        WorkOrder result = service.assignTechnician(10L, 5L);

        assertEquals(tech, result.getTechnician());
        assertEquals("ASIGNADA", result.getEstado());
    }

    @Test
    void assignTechnician_shouldThrow_whenOrderNotFound() {
        when(workRepo.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.assignTechnician(99L, 1L);
        });

        assertEquals("Orden no encontrada", ex.getMessage());
    }

    @Test
    void assignTechnician_shouldThrow_whenTechnicianNotFound() {
        when(workRepo.findById(10L)).thenReturn(Optional.of(new WorkOrder()));
        when(techRepo.findById(50L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.assignTechnician(10L, 50L);
        });

        assertEquals("Técnico no encontrado", ex.getMessage());
    }

    @Test
    void updateStatus_shouldChangeAndSave() {
        WorkOrder wo = new WorkOrder();
        wo.setIdOrder(7L);

        when(workRepo.findById(7L)).thenReturn(Optional.of(wo));
        when(workRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        WorkOrder updated = service.updateStatus(7L, "COMPLETADA");

        assertEquals("COMPLETADA", updated.getEstado());
    }

    @Test
    void updateStatus_shouldThrow_whenOrderNotFound() {
        when(workRepo.findById(123L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.updateStatus(123L, "X");
        });

        assertEquals("Orden no encontrada", ex.getMessage());
    }
}
