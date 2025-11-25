package com.telconovaP7F22025.demo.service.impl;

import org.springframework.stereotype.Service;
import com.telconovaP7F22025.demo.dto.order.CreateOrderRequest;
import com.telconovaP7F22025.demo.model.WorkOrder;
import com.telconovaP7F22025.demo.model.Technician;
import com.telconovaP7F22025.demo.repository.WorkOrderRepository;
import com.telconovaP7F22025.demo.repository.TechnicianRepository;
import com.telconovaP7F22025.demo.service.WorkOrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkOrderServiceImpl implements WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final TechnicianRepository technicianRepository; // Inyectamos repo de técnicos

    @Override
    @Transactional
    public WorkOrder createWorkOrder(CreateOrderRequest request) {
        WorkOrder order = new WorkOrder();
        order.setNameCliente(request.nameCliente());
        order.setPhoneCliente(request.phoneCliente());
        order.setTipoServicio(request.tipoServicio());
        order.setPrioridad(request.prioridad());
        order.setDescripcion(request.descripcion());
        order.setEstado("PENDIENTE");
        return workOrderRepository.save(order);
    }

    @Override
    @Transactional
    public List<WorkOrder> getAllOrders() {
        return workOrderRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<WorkOrder> getOrderById(Long id) {
        return workOrderRepository.findById(id);
    }

    @Override
    @Transactional
    public WorkOrder assignTechnician(Long orderId, Long technicianId) {
        // 1. Buscar la orden
        WorkOrder order = workOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // 2. Buscar el técnico
        Technician tech = technicianRepository.findById(technicianId)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        // 3. Asignar
        order.setTechnician(tech);
        order.setEstado("ASIGNADA"); // Actualizamos estado automáticamente
        
        return workOrderRepository.save(order);
    }

    @Override
    @Transactional
    public WorkOrder updateStatus(Long orderId, String newStatus) {
        WorkOrder order = workOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        
        order.setEstado(newStatus);
        return workOrderRepository.save(order);
    }
}