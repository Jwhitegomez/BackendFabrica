package com.telconovaP7F22025.demo.controller;
<<<<<<< HEAD
=======

>>>>>>> main
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.telconovaP7F22025.demo.service.AutService;
<<<<<<< HEAD
=======
import com.telconovaP7F22025.demo.config.JwtUtil;
>>>>>>> main
import com.telconovaP7F22025.demo.dto.aut.LoginRequest;
import com.telconovaP7F22025.demo.dto.aut.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
<<<<<<< HEAD


=======
import java.util.Map;
>>>>>>> main

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
<<<<<<< HEAD
@Tag(name = "Authentication Controller", description = "Handles user authentication")
public class AutController {
    private final AutService autService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = autService.authenticateUser(loginRequest);
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
=======
@Tag(name = "Authentication Controller", description = "Autenticación y Registro")
public class AutController {

    private final AutService autService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = autService.authenticateUser(loginRequest);
        if (isAuthenticated) {
            // Generar y devolver Token
            String token = jwtUtil.generateToken(loginRequest.email());
            return ResponseEntity.ok(Map.of("token", token, "message", "Login exitoso"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
>>>>>>> main
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        boolean created = autService.registerUser(registerRequest);
        if (created) {
<<<<<<< HEAD
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with that email already exists");
        }
    }
}

=======
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }
    }
}
>>>>>>> main
