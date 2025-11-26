package com.telconovaP7F22025.demo.controller;

import com.telconovaP7F22025.demo.dto.order.CreateOrderRequest;
import com.telconovaP7F22025.demo.dto.order.UpdateStatusRequest;
import com.telconovaP7F22025.demo.model.WorkOrder;
import com.telconovaP7F22025.demo.service.WorkOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkOrderControllerTest {

    @Mock
    private WorkOrderService workOrderService;

    @InjectMocks
    private WorkOrderController controller;

    private WorkOrder sampleOrder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sampleOrder = new WorkOrder();
        sampleOrder.setIdOrder(1L);
        sampleOrder.setNameCliente("Pedro");
        sampleOrder.setEstado("PENDIENTE");
    }

    // 1. Crear orden
    @Test
    void testCreateOrder() {
        CreateOrderRequest request = new CreateOrderRequest("Pedro", "123", "Fibra", "ALTA", "Desc");

        when(workOrderService.createWorkOrder(request)).thenReturn(sampleOrder);

        ResponseEntity<WorkOrder> response = controller.createOrder(request);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(sampleOrder, response.getBody());
    }

    // 2. Obtener todas las órdenes
    @Test
    void testGetAllOrders() {
        when(workOrderService.getAllOrders()).thenReturn(List.of(sampleOrder));

        ResponseEntity<List<WorkOrder>> response = controller.getAllOrders();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    // 3. Buscar orden por ID existente
    @Test
    void testGetOrderByIdFound() {
        when(workOrderService.getOrderById(1L)).thenReturn(Optional.of(sampleOrder));

        ResponseEntity<WorkOrder> response = controller.getOrderById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleOrder, response.getBody());
    }

    // 4. Buscar orden por ID que no existe
    @Test
    void testGetOrderByIdNotFound() {
        when(workOrderService.getOrderById(1L)).thenReturn(Optional.empty());

        ResponseEntity<WorkOrder> response = controller.getOrderById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    // 5. Asignar técnico correctamente
    @Test
    void testAssignTechnicianSuccess() {
        when(workOrderService.assignTechnician(1L, 2L)).thenReturn(sampleOrder);

        ResponseEntity<WorkOrder> response = controller.assignTechnician(1L, 2L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleOrder, response.getBody());
    }

    // 6. Asignar técnico lanza excepción → 400
    @Test
    void testAssignTechnicianError() {
        when(workOrderService.assignTechnician(1L, 2L)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<WorkOrder> response = controller.assignTechnician(1L, 2L);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    // 7. Update estado correctamente
    @Test
    void testUpdateStatusSuccess() {
        UpdateStatusRequest req = new UpdateStatusRequest("FINALIZADA");

        when(workOrderService.updateStatus(1L, "FINALIZADA")).thenReturn(sampleOrder);

        ResponseEntity<WorkOrder> response = controller.updateStatus(1L, req);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleOrder, response.getBody());
    }

    // 8. Update estado lanza excepción → 404
    @Test
    void testUpdateStatusNotFound() {
        UpdateStatusRequest req = new UpdateStatusRequest("FINALIZADA");

        when(workOrderService.updateStatus(1L, "FINALIZADA"))
                .thenThrow(new RuntimeException("Orden no encontrada"));

        ResponseEntity<WorkOrder> response = controller.updateStatus(1L, req);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
