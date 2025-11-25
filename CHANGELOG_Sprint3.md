# Changelog - TelcoNova SupportSuite

Todos los cambios notables en el proyecto serán documentados en este archivo.

## [Sprint 3] - 2025-11-24

### Nuevas Características (Features)
- **Gestión de Órdenes de Trabajo (Work Orders):**
  - Implementado endpoint `POST /api/orders` para el **Registro de Órdenes de Trabajo**.
  - Implementado endpoint `GET /api/orders` para listar todas las órdenes (Dashboard).
  - Implementado endpoint `GET /api/orders/{id}` para ver detalles.
  - Implementado endpoint `PUT /api/orders/{id}/assign/{techId}` para asignar un Técnico a una Orden.
  - Implementado endpoint `PATCH /api/orders/{id}/status` para actualizar el estado (PENDIENTE -> EN_PROGRESO -> FINALIZADA).

- **Seguridad Avanzada (JWT):**
  - Implementación de `JwtUtil` y `JwtRequestFilter` para seguridad basada en Tokens.
  - Endpoint `POST /api/auth/login` ahora devuelve un Token JWT en lugar de una sesión básica.
  - Configuración de **CORS** global para permitir peticiones desde Frontend (Localhost 3000/4200/5173).

- **Auditoría de Datos (Triggers):**
  - Creada tabla `auditoria_ordenes` en base de datos.
  - Implementado Trigger nativo Java (`AuditTrigger`) en H2 que registra automáticamente cambios de estado en las órdenes.

### 🛠 Cambios Técnicos (Refactor)
- **Modelo de Datos (MER):**
  - Actualizada entidad `WorkOrder` para incluir relación `@ManyToOne` con `Technician`.
  - Actualizado script `schema.sql` para incluir claves foráneas y datos de prueba masivos (Seed Data).
- **Dependencias:**
  - Agregadas librerías `jjwt` (JSON Web Token) en `pom.xml`.
  - Ajustado scope de `h2database` para permitir compilación de Triggers.

### Pruebas
- Agregadas pruebas de integración (`WorkOrderIntegrationTest`) para validar el flujo de creación de órdenes con seguridad habilitada.