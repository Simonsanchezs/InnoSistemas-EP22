package com.example.InnoSistemas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "dashboard")
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dashboard_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    @Column(name = "tareas_completadas")
    private int tareas_completadas;

    @Column(name = "entregas")
    private int entregas;

    @Column(name = "estado")
    private String estado;

}
