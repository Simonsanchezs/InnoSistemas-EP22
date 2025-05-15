package com.example.InnoSistemas.service;

import com.example.InnoSistemas.entity.Curso;
import com.example.InnoSistemas.entity.Estudiante;
import com.example.InnoSistemas.repository.CursoRepository;
import com.example.InnoSistemas.repository.EstudianteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;

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

//    public Curso findByIdWithEstudiantes(int id) {
//        return cursoRepository.findByIdWithEstudiantes(id)
//                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
//    }

    // Operaciones específicas para la relación con estudiantes
    public List<Estudiante> getEstudiantesByCursoId(Integer cursoId) {
        Curso curso = cursoRepository.findByIdWithEstudiantes(cursoId)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));
        return curso.getEstudianteList();
    }

    // Versión optimizada con FetchType.LAZY
    public void addEstudianteToCurso(Integer cursoId, Integer estudianteId) {
        // Versión eficiente que no depende del FetchType
        cursoRepository.addEstudianteToCurso(cursoId, estudianteId);
    }

    // Versión optimizada con FetchType.LAZY
    public void deleteEstudianteFromCurso(Integer cursoId, Integer estudianteId) {
        // Versión eficiente que no depende del FetchType
        cursoRepository.removeEstudianteFromCurso(cursoId, estudianteId);
    }

}
