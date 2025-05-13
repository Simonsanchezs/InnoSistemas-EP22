package com.example.InnoSistemas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "estudiante")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estudiante_id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "email")
    private String email;

    @ManyToMany(mappedBy = "estudianteList")
    @JsonIgnore
    private List<Curso> cursoList;

    @OneToMany(mappedBy = "estudiante")
    @JsonIgnore
    private List<Integracion> integracionList;

    @OneToMany(mappedBy = "estudiante")
    @JsonIgnore
    private List<Notificacion> notificacionList;
}
