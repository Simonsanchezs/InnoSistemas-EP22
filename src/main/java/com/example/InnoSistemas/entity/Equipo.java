package com.example.InnoSistemas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "equipo")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipo_id")
    private int id;

    @Column(name = "nombre_equipo")
    private String nombre;

    @Column(name = "fecha_creacion")
    private LocalDateTime fecha_creacion;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "equipo")
    @JsonIgnore
    private List<Integracion> integracionList;

    @OneToMany(mappedBy = "equipo")
    @JsonIgnore
    private List<Notificacion> notificacionList;

    @OneToMany(mappedBy = "equipo")
    @JsonIgnore
    private List<DashBoard> dashBoardList;
}
