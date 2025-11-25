# Backend del Proyecto: TelcoNova SupportSuite

Este repositorio contiene el código fuente del servicio backend para el proyecto **TelcoNova SupportSuite**, desarrollado como parte de la iniciativa Fábrica Escuela.

El objetivo de este backend es proveer una API RESTful robusta para gestionar el registro, asignación y seguimiento de órdenes de trabajo de soporte técnico.

---

## Contexto Académico (Fábrica Escuela 2025-2)

Este proyecto es el entregable central para las siguientes materias de Ingeniería de Sistemas en la Universidad de Antioquia:

| Materia | Profesor |
| :--- | :--- |
| **Bases de Datos y Laboratorio** | John Jairo Prado Piedrahita |
| **Arquitectura de Software** | Didier Correa Londoño |

### Integrantes del Equipo
* Cristian David Diez Lopez
* Roller Andrés Hernández López

---

## Stack Tecnológico

Este backend está construido con una arquitectura moderna de Java, enfocada en la mantenibilidad y el despliegue.

* **Framework:** Spring Boot 3
* **Lenguaje:** Java 21
* **Seguridad:** Spring Security (con hashing BCrypt)
* **Base de Datos:** Spring Data JPA / Hibernate
* [cite_start]**Base de Datos (Desarrollo):** H2 (En memoria) [cite: 310-311]
* **Validación:** `jakarta.validation`
* [cite_start]**Documentación API:** SpringDoc (OpenAPI 3 / Swagger) [cite: 312-313]
* **Build:** Apache Maven
* **Monitoreo:** Spring Boot Actuator + Micrometer (para Prometheus)
* **Containerización:** Docker

---

## Cómo Ejecutar Localmente

1.  **Clonar el repositorio:**
    ```sh
    git clone [https://github.com/codeFactory20252Feature7/BackendFabrica.git](https://github.com/codeFactory20252Feature7/BackendFabrica.git)
    cd BackendFabrica
    ```

2.  **Ejecutar la aplicación (con Maven Wrapper):**
    *En Windows:*
    ```sh
    ./mvnw spring-boot:run
    ```
    *En macOS/Linux:*
    ```sh
    ./mvnw spring-boot:run
    ```

La aplicación estará disponible en `http://localhost:8080`.

---

## Documentación y Endpoints de la API

La documentación completa de la API se genera automáticamente con Swagger y está disponible una vez que la aplicación está en ejecución.

* **Interfaz de Swagger (UI):** `http://localhost:8080/swagger-ui.html`
* **Definición OpenAPI (JSON):** `http://localhost:8080/v3/api-docs`

### Endpoints Principales

* `POST /api/auth/register`: Registra un nuevo usuario (`User`).
* `POST /api/auth/login`: Autentica un usuario.
* `POST /api/technicians/create`: Crea un nuevo técnico (`Technician`).
* `GET /api/technicians/all`: Lista todos los técnicos.
* `POST /api/orders`: (Nuevo) Crea una nueva orden de trabajo (`WorkOrder`).

---

## Despliegue con Docker

El proyecto incluye un `Dockerfile` optimizado (multi-etapa) para crear una imagen de producción ligera.

1.  **Construir la imagen de Docker:**
    ```sh
    docker build -t telconova-backend .
    ```

2.  **Ejecutar el contenedor:**
    ```sh
    docker run -p 8080:8080 telconova-backend
    ```