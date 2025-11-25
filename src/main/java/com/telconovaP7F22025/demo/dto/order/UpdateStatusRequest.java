package com.telconovaP7F22025.demo.dto.order;

import jakarta.validation.constraints.NotBlank;

public record UpdateStatusRequest(
    @NotBlank String nuevoEstado // Ej: "EN_PROGRESO", "FINALIZADA"
) {}