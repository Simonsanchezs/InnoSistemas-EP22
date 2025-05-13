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
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curso_id")
    private int id;

    @Column(name = "nombre_curso")
    private String nombre;

    @Column(name = "semestre")
    private String semestre;

    @ManyToMany()
    @JoinTable(
        name = "estudiante_curso",
        joinColumns = @JoinColumn(name = "curso_id", referencedColumnName = "curso_id"),
        inverseJoinColumns = @JoinColumn(name = "estudiante_id", referencedColumnName = "estudiante_id")
    )
    private List<Estudiante> estudianteList;

    @OneToMany(mappedBy = "curso")
    @JsonIgnore
    private List<Equipo> equipoList;
}
