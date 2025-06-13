package com.example.innosistemas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.innosistemas.entity.Equipo;
import com.example.innosistemas.entity.Estudiante;
import com.example.innosistemas.entity.Notificacion;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByEstudiante(Estudiante estudiante);
    List<Notificacion> findByEquipo(Equipo equipo);
}
