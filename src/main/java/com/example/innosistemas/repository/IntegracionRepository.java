package com.example.innosistemas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.innosistemas.entity.Equipo;
import com.example.innosistemas.entity.Estudiante;
import com.example.innosistemas.entity.Integracion;

import java.util.List;

@Repository
public interface IntegracionRepository extends JpaRepository<Integracion, Integer> {
    List<Integracion> findByEstudiante(Estudiante estudiante);
    List<Integracion> findByEquipo(Equipo equipo);
}
