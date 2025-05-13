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
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private int id;

    @Column(name = "nombre_rol")
    private String nombre;

    @OneToMany(mappedBy = "rol")
    @JsonIgnore
    private List<Integracion> integracionList;
}
