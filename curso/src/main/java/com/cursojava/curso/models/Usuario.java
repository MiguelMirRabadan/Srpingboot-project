package com.cursojava.curso.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="usuarios")
public class Usuario {

    @Getter @Setter @Column(name="ID")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter @Setter @Column(name="nombre")
    private String nombre;

    @Getter @Setter @Column(name="apellidos")
    private String apellido;

    @Getter @Setter @Column(name="tlf")
    private String telefono;

    @Getter @Setter @Column(name="email")
    private String email;

    @Getter @Setter @Column(name="password")
    private String password;

}
