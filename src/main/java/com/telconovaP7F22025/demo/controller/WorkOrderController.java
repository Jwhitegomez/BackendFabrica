package com.telconovaP7F22025.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telconovaP7F22025.demo.dto.order.CreateOrderRequest;
import com.telconovaP7F22025.demo.dto.order.UpdateStatusRequest;
import com.telconovaP7F22025.demo.model.WorkOrder;
import com.telconovaP7F22025.demo.service.WorkOrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "WorkOrder Controller", description = "Gestión completa de Órdenes de Trabajo")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    // 1. Crear Orden
    @PostMapping
    @Operation(summary = "Crear nueva orden", description = "Crea una orden en estado PENDIENTE")
    public ResponseEntity<WorkOrder> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        WorkOrder newOrder = workOrderService.createWorkOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    // 2. Listar todas (Para el Dashboard del admin)
    @GetMapping
    @Operation(summary = "Listar órdenes", description = "Devuelve todas las órdenes registradas")
    public ResponseEntity<List<WorkOrder>> getAllOrders() {
        return ResponseEntity.ok(workOrderService.getAllOrders());
    }

    // 3. Obtener detalle de una orden
    @GetMapping("/{id}")
    @Operation(summary = "Buscar orden por ID")
    public ResponseEntity<WorkOrder> getOrderById(@PathVariable Long id) {
        return workOrderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Asignar Técnico a Orden
    @PutMapping("/{orderId}/assign/{techId}")
    @Operation(summary = "Asignar técnico", description = "Asocia un técnico existente a una orden")
    public ResponseEntity<WorkOrder> assignTechnician(@PathVariable Long orderId, @PathVariable Long techId) {
        try {
            return ResponseEntity.ok(workOrderService.assignTechnician(orderId, techId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // O un mensaje de error más detallado
        }
    }

    // 5. Cambiar Estado (Ej: De ASIGNADA a FINALIZADA)
    @PatchMapping("/{orderId}/status")
    @Operation(summary = "Actualizar estado", description = "Cambia el estado de la orden (ej. EN_PROGRESO, FINALIZADA)")
    public ResponseEntity<WorkOrder> updateStatus(@PathVariable Long orderId, @Valid @RequestBody UpdateStatusRequest request) {
        try {
            return ResponseEntity.ok(workOrderService.updateStatus(orderId, request.nuevoEstado()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}