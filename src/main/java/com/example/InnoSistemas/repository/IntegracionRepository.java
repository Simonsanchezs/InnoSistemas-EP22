package com.example.InnoSistemas.repository;

import com.example.InnoSistemas.entity.Equipo;
import com.example.InnoSistemas.entity.Estudiante;
import com.example.InnoSistemas.entity.Integracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntegracionRepository extends JpaRepository<Integracion, Integer> {
    List<Integracion> findByEstudiante(Estudiante estudiante);
    List<Integracion> findByEquipo(Equipo equipo);
}
