package com.telconovaP7F22025.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telconovaP7F22025.demo.model.WorkOrder;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    // Por ahora, los métodos estándar de JpaRepository son suficientes
}