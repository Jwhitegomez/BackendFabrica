package com.telconovaP7F22025.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

@Entity @Table(name = "usuarios")
public class User {

    @Id @Column(name = "idUsuario") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name = "emailUsuario", nullable = false, unique = true)
    private String emailUsuario;

    @Column(name = "passwordUsuario", nullable = false)
    private String passwordUsuario;
    
    @Column(name = "nameUsuario", nullable = false)
    private String name;

    @Column(name = "roleUsuario", nullable = false)
    private String role;
}
