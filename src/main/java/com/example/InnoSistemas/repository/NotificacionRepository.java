package com.example.InnoSistemas.repository;

import com.example.InnoSistemas.entity.Equipo;
import com.example.InnoSistemas.entity.Estudiante;
import com.example.InnoSistemas.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByEstudiante(Estudiante estudiante);
    List<Notificacion> findByEquipo(Equipo equipo);
}
