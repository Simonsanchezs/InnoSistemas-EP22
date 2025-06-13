package com.example.innosistemas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.innosistemas.entity.Curso;
import com.example.innosistemas.entity.Equipo;


import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {
    List<Equipo> findByCurso(Curso curso);
}

