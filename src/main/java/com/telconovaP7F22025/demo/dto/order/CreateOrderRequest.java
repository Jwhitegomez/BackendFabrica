package com.telconovaP7F22025.demo.dto.order;

import jakarta.validation.constraints.NotBlank;

// Este es el JSON que esperamos del Frontend
// No incluimos id, estado, ni fechas, ya que se manejan en el backend.
public record CreateOrderRequest(
    @NotBlank String nameCliente,
    @NotBlank String phoneCliente,
    @NotBlank String tipoServicio,
    @NotBlank String prioridad,
    String descripcion
) {}