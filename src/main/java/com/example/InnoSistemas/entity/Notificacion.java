package com.example.InnoSistemas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "notificacion")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacion_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "fecha_envio")
    private LocalDateTime fecha_envio;

    @Column(name = "leida")
    private boolean leida;
}
