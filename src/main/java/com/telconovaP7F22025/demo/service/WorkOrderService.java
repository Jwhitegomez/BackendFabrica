package com.telconovaP7F22025.demo.service;

import com.telconovaP7F22025.demo.dto.order.CreateOrderRequest;
import com.telconovaP7F22025.demo.model.WorkOrder;
import java.util.List;
import java.util.Optional;

public interface WorkOrderService {
    
    WorkOrder createWorkOrder(CreateOrderRequest request);

    // --- NUEVOS MÉTODOS ---
    List<WorkOrder> getAllOrders();
    
    Optional<WorkOrder> getOrderById(Long id);
    
    WorkOrder assignTechnician(Long orderId, Long technicianId);
    
    WorkOrder updateStatus(Long orderId, String newStatus);
}