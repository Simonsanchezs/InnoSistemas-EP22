package com.example.InnoSistemas.repository;

import com.example.InnoSistemas.entity.Curso;
import com.example.InnoSistemas.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {
    List<Equipo> findByCurso(Curso curso);
}

