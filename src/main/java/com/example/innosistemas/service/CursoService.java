package com.example.innosistemas.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.innosistemas.entity.Curso;
import com.example.innosistemas.entity.Estudiante;
import com.example.innosistemas.repository.CursoRepository;

import java.util.List;


@Service
@Transactional
public class CursoService {

    private final CursoRepository cursoRepository;

    @Autowired
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    public Curso findById(int id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
    }

    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso update(int id, Curso cursoDetails) {
        Curso curso = findById(id);
        curso.setNombre(cursoDetails.getNombre());
        curso.setSemestre(cursoDetails.getSemestre());
        return cursoRepository.save(curso);
    }

    public void deleteById(int id) {
        cursoRepository.deleteById(id);
    }

    public List<Estudiante> getEstudiantesByCursoId(Integer cursoId) {
        Curso curso = cursoRepository.findByIdWithEstudiantes(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));
        return curso.getEstudianteList();
    }

    public void addEstudianteToCurso(Integer cursoId, Integer estudianteId) {
        cursoRepository.addEstudianteToCurso(cursoId, estudianteId);
    }

    public void deleteEstudianteFromCurso(Integer cursoId, Integer estudianteId) {
        cursoRepository.removeEstudianteFromCurso(cursoId, estudianteId);
    }
}