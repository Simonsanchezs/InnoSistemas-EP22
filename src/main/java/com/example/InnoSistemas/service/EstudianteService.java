package com.example.innosistemas.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.innosistemas.entity.Curso;
import com.example.innosistemas.entity.Estudiante;
import com.example.innosistemas.repository.EstudianteRepository;


import java.util.List;

@Service
@Transactional
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    public Estudiante findById(int id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public Estudiante update(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public void deleteById(int id) {
        estudianteRepository.deleteById(id);
    }

    // Operaciones específicas para la relación con cursos
    public List<Curso> getCursosByEstudianteId(Integer estudianteId) {
        Estudiante estudiante = estudianteRepository.findByIdWithCursos(estudianteId)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));
        return estudiante.getCursoList();
    }
}
